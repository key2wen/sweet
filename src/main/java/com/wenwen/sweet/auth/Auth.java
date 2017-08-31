package com.wenwen.sweet.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置类或方法访问需要的角色类型, 方法配置将会覆盖类配置
 * @author yunxiang.zyx
 * @date 16/2/15
 * @see com.wenwen.sweet.model.UserInfo.RoleType
 * @see
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Auth {
    int[] value();
}
