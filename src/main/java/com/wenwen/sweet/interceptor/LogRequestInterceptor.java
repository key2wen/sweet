package com.wenwen.sweet.interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LogRequestInterceptor.java
 *
 * @author ls
 * @version 1.0
 * @date 2016-01-30
 *
 * 描述：记录各种请求操作
 */
public class LogRequestInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(LogRequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String query = request.getQueryString();
        String url = request.getRequestURI() + (StringUtils.isEmpty(query) ? ""
                : "?" + query);
        Object user = request.getSession().getAttribute("currUser");
        log.debug("request: {}, parameters: {}, ip: {}, user: {}", url, JSON.toJSON(request.getParameterMap()), request.getRemoteAddr(),  String.valueOf(user));
        return true;
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        log.debug(modelAndView.toString());
//        super.postHandle(request, response, handler, modelAndView);
//    }
}
