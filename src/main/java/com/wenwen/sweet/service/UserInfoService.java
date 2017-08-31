package com.wenwen.sweet.service;


import com.wenwen.sweet.commons.PagedResult;
import com.wenwen.sweet.model.UserInfo;

import java.util.List;

/**
 * UserInfoService.java
 *
 * @version 1.0
 * @date 2016-01-30
 * @author ls
 *
 * 描述：用户信息接口
 */
public interface UserInfoService {

    /**
     * 登录
     * @return true 校验成功
     */
    UserInfo login(String username, String password);

    /**
     * 通过微信openid登陆
     */
    UserInfo loginByOpenid(String openid);

    /**
     * 登录校验
     * @return true 校验成功
     */
    boolean loginCheck(String username, String password);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    UserInfo getUserInfo(int id);

    /**
     * 根据分页信息，查询普通用户集合
     * @param pageNum
     * @param pageSize
     * @return
     */
    PagedResult<UserInfo> searchCommonUserByPage(Integer pageNum, Integer pageSize);

    /**
     * 删除用户，逻辑删除
     * @param id
     */
    void delete(int id);

    /**
     * 新增或更新用户信息
     * @return
     */
    int saveOrUpdate(UserInfo userInfo, boolean needUpdatePassWord);

    /**
     * 用户名是否存在
     * @param username
     * @return
     */
    boolean isUsernameExist(String username);

    /**
     * 重置用户密码
     * @param id
     */
    int resetPassword(int id);

    /**
     * 批量查询用户信息
     * @param ids
     * @return
     */
    List<UserInfo> getUserInfoByIds(List<Integer> ids);

    UserInfo getUserInfoByOpenid(String openid);

    int bindWeixin(int userId, String openid);

    /**
     * 修改密码
     * @param id
     * @param password
     * @return
     */
    public int modifyPassword(int id, String password);
}
