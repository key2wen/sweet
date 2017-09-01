package com.wenwen.sweet.interceptor;

import com.wenwen.sweet.auth.Auth;
import com.wenwen.sweet.auth.AuthUtils;
import com.wenwen.sweet.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by ls on 15-4-24.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    private List<String> weixinUrls;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");

        HttpSession session = request.getSession();
        Object user = session.getAttribute("currUser");
        if (user == null) {
            request.getSession().setAttribute("originRefUrl", requestUrl);
            if (weixinUrls != null && weixinUrls.contains(requestUrl)) {
                response.sendRedirect("/register");
            } else {
                response.sendRedirect("/login");
            }
            return false;
        }
        return AuthUtils.checkHttpReqAuth(request, response, handler, user);
    }

    public List<String> getWeixinUrls() {
        return weixinUrls;
    }

    public void setWeixinUrls(List<String> weixinUrls) {
        this.weixinUrls = weixinUrls;
    }
}
