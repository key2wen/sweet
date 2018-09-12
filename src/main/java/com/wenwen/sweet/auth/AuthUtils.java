package com.wenwen.sweet.auth;

import com.wenwen.sweet.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zwh
 */
public class AuthUtils {

    private static final int[] ADMIN_ROLES = {UserInfo.RoleType.ADMIN_USER};

    static Logger logger = LoggerFactory.getLogger(AuthUtils.class);

    public static boolean checkHttpReqAuth(HttpServletRequest request, HttpServletResponse response, Object handler, Object userInfo) throws Exception {

        Auth auth = null;

        if (handler instanceof HandlerMethod) {

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 先检查方法权限配置,如果没有配置,再查找类配置
            auth = handlerMethod.getMethod().getAnnotation(Auth.class);
            if (auth == null) {
                auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
            }

        } else if (handler instanceof ParameterizableViewController) {
            //当作系统用户权限处理
            //处理这种请求路径： <mvc:view-controller path="/qrcode/list" view-name="/qrcode/generate"/>
            auth = null;

        } else {
            response.sendRedirect("/denied");
            return false;
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
        return false;
    }

    public static boolean isSystemAuth(HttpServletRequest request) {
        try {
            Object object = request.getSession().getAttribute("currUser");
            if (object != null) {
                UserInfo userInfo = (UserInfo) object;
                if (UserInfo.RoleType.ADMIN_USER == userInfo.getRoleType()) {
                    return true;
                }
            }
        } catch (Throwable e) {
            logger.error("error : {}", e.getMessage(), e);
            return false;
        }
        return false;
    }
}
