package com.wenwen.sweet.model;

import com.wenwen.sweet.commons.BaseBean;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * UserInfo.java
 *
 * @version 1.0
 * @date 2016-01-30
 * @author ls
 *
 * 描述：用户信息实体类
 */
public class UserInfo extends BaseBean {

    /**
     * 初始默认密码
     */
    public static final String DEFAULT_PASSWORD = "000000";

    /**
     * 用户名，唯一索引
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名只能是3-20个字符")
    private String username;

    /**
     * 姓名
     */
    @NotBlank(message = "真实姓名不能为空")
    @Size(min = 2, max = 20, message = "真实姓名只能是2-20个字符")
    private String name;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码只能是6-20个字符")
    private String password;

    @NotBlank(message = "手机号码不能为空")
    @Size(min = 11, max = 11, message = "手机号码只能是11个字符")
    private String phone;

    @NotNull(message = "部门不能为空")
    private Integer workDepartment;

    @NotBlank(message = "电子邮箱不能为空")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", message = "邮箱格式不正确")
    private String email;

    private String features;

    private Integer roleType;

    private Integer status;

    private String openid;

    // 证书地址
    private String certificatePath;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Integer getWorkDepartment() {
        return workDepartment;
    }
    public void setWorkDepartment(Integer workDepartment) {
        this.workDepartment = workDepartment;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFeatures() {
        return features;
    }
    public void setFeatures(String features) {
        this.features = features;
    }
    public Integer getRoleType() {
        return roleType;
    }
    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    /**
     * 静态内部类：用户角色类型
     */
    public static class RoleType {
        /**
         * 未支付用户
         */
        public static final int NON_PAYMENT_USER = 0;
        /**
         * 普通用户
         */
        public static final int COMMON_USER= 1;

        /**
         * 管理员
         */
        public static final int ADMIN_USER = 2;
    }

    /**
     * 静态内部类：用户状态
     */
    public static class Status{

        /**
         * 删除
         */
        public static final int DELETE = -1;
    }

    public static class RegisterStep {
        public static final int CLOSE = -1;
        public static final int REGISTER = 0;
        public static final int LOGIN = 0;
        public static final int PAY = 1;
        public static final int BIND = 2;
    }

}

