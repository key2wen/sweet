package com.wenwen.sweet.dao.mapper;

import com.wenwen.sweet.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserInfoMapper.java
 *
 * @version 1.0
 * @date 2016-01-30
 * @author ls
 *
 * 描述：用户信息DAO类
 */
public interface UserInfoMapper {

    /**
     * 根据用户名密码查询用户信息
     * @param username
     * @param password
     * @return
     */
    UserInfo selectByNameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户名密码统计个数
     * @param username
     * @param password
     * @return
     */
    int selectCountByNameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据id查询用户信息
     * @return
     */
    UserInfo getUserInfoById(int id);

    /**
     * 根据类型和分页信息，查询用户集合
     * @param offset
     * @param limit
     * @param roleType UserInfo.RoleType
     * @return
     */
    List<UserInfo> selectListByType(@Param("offset") Integer offset, @Param("limit") Integer limit, @Param("roleType") Integer roleType);

    /**
     * 根据类型统计个数
     * @param roleType
     * @return
     */
    int countByType(@Param("roleType") Integer roleType);

    /**
     * 更新用户的状态
     * @param id
     * @param status
     * @return
     */
    int updateUserStatusById(@Param("id") int id, @Param("status") int status);

    /**
     * 新增用户
     * @param userInfo
     * @return
     */
    int insertUserInfo(UserInfo userInfo);

    /**
     * 更新用户信息
     * @param userInfo
     * @return
     */
    int updateUserInfo(UserInfo userInfo);

    /**
     * 根据用户名统计用户个数
     * @param username
     * @return
     */
    int selectCountByName(String username);

    /**
     * 根据微信openId获取用户信息
     * @param openid
     * @return
     */
    UserInfo selectByOpenid(String openid);

    /**
     * 批量查询用户信息
     * @param ids
     * @return
     */
    List<UserInfo> getUserInfoByIds(@Param("ids") List<Integer> ids);
}
