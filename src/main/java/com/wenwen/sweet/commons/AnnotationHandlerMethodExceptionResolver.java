package com.wenwen.sweet.commons;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * 不必在Controller中对异常进行处理，抛出即可，由此异常解析器统一控制。<br>
 * ajax请求（有@ResponseBody的Controller）发生错误，输出JSON。<br>
 * 页面请求（无@ResponseBody的Controller）发生错误，输出错误页面。<br>
 * 需要与AnnotationMethodHandlerAdapter使用同一个messageConverters<br>
 * Controller中需要有专门处理异常的方法。
 * <p>
 * 可以在Controller里加上@ExceptionHandler(RuntimeException.class) 来个性化异常处理
 *
 * @author yunxiang.zhang
 * @date 2016年1月30日
 */
public class AnnotationHandlerMethodExceptionResolver extends ExceptionHandlerExceptionResolver implements Ordered {

    private String defaultErrorView;

    /**
     * 最先使用这个类处理异常
     */
    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    public String getDefaultErrorView() {
        return defaultErrorView;
    }

    public void setDefaultErrorView(String defaultErrorView) {
        this.defaultErrorView = defaultErrorView;
    }

    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
                                                           HandlerMethod handlerMethod, Exception exception) {

        if (logger.isErrorEnabled()) {
            logger.error(ExceptionUtils.getFullStackTrace(exception));
        }

        if (handlerMethod == null)
            return null;

        Method method = handlerMethod.getMethod();

        if (method == null) {
            return null;
        }

        ResponseBody responseBodyAnn = AnnotationUtils.findAnnotation(method, ResponseBody.class);
        if (responseBodyAnn != null) {
            try {
                ResponseStatus responseStatusAnn = AnnotationUtils.findAnnotation(method, ResponseStatus.class);
                if (responseStatusAnn != null) {
                    HttpStatus responseStatus = responseStatusAnn.value();
                    String reason = responseStatusAnn.reason();
                    if (!StringUtils.hasText(reason)) {
                        response.setStatus(responseStatus.value());
                    } else {
                        try {
                            response.sendError(responseStatus.value(), reason);
                        } catch (IOException e) {
                        }
                    }
                }
                return handleException(exception, request, response);
            } catch (Exception e) {
                return null;
            }
        }

        ModelAndView returnValue = new ModelAndView();
        returnValue.setViewName(defaultErrorView);
        return returnValue;
    }

    private ModelAndView handleException(Exception exception, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpInputMessage inputMessage = new ServletServerHttpRequest(request);
        List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();
        if (acceptedMediaTypes.isEmpty()) {
            acceptedMediaTypes = Collections.singletonList(MediaType.ALL);
        }
        MediaType.sortByQualityValue(acceptedMediaTypes);
        ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        if (messageConverters != null) {
            for (HttpMessageConverter<?> converter : messageConverters) {
                if (converter instanceof FastJsonHttpMessageConverter) {
                    FastJsonHttpMessageConverter jsonConverter = (FastJsonHttpMessageConverter) converter;
                    for (MediaType acceptedMediaType : acceptedMediaTypes) {
                        if (jsonConverter.canWrite(JsonWrapper.class, acceptedMediaType)) {
                            jsonConverter.write(getExceptionJsonWrapper(exception), acceptedMediaType, outputMessage);
                            outputMessage.close();
                            return new ModelAndView();
                        }
                    }
                }
            }
        }
        outputMessage.close();
        return null;
    }

    private JsonWrapper<Object> getExceptionJsonWrapper(Exception exception) {
        JsonWrapper<Object> result = new JsonWrapper<Object>();
        result.setResult(JsonWrapper.EMPTY_OBJECT);
        result.setFailStatus(exception + ":" + exception.getMessage());
        return result;
    }

}
