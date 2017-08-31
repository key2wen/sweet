package com.wenwen.sweet.interceptor;

import com.wenwen.sweet.auth.Auth;
import com.wenwen.sweet.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by ls on 15-4-24.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    private static final int[] ADMIN_ROLES = {UserInfo.RoleType.ADMIN_USER};
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
        return checkAuth(request, response, handler, user);
    }

    private boolean checkAuth(HttpServletRequest request, HttpServletResponse response, Object handler, Object userInfo) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 先检查方法权限配置,如果没有配置,再查找类配置
        Auth auth = handlerMethod.getMethod().getAnnotation(Auth.class);
        if (auth == null) {
            auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
        }

        int[] allowRoleTypes = null;
        // 如果没有配置权限,则只允许管理员访问
        if (auth == null) {
            allowRoleTypes = ADMIN_ROLES;
        } else {
            allowRoleTypes = auth.value();
        }

        UserInfo user = (UserInfo) userInfo;
        if (user != null) {
            if (user.getRoleType() == UserInfo.RoleType.ADMIN_USER) {
                return true;
            }
            for (int allowRoleType : allowRoleTypes) {
                if (user.getRoleType() == allowRoleType) {
                    return true;
                }
            }
        }
        response.sendRedirect("/denied");
        return true;
    }

    public List<String> getWeixinUrls() {
        return weixinUrls;
    }

    public void setWeixinUrls(List<String> weixinUrls) {
        this.weixinUrls = weixinUrls;
    }
}
