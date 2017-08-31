package com.wenwen.sweet.service.impl;

import com.google.common.collect.Lists;
import com.wenwen.sweet.commons.PagedResult;
import com.wenwen.sweet.dao.mapper.UserInfoMapper;
import com.wenwen.sweet.model.UserInfo;
import com.wenwen.sweet.service.UserInfoService;
import com.wenwen.sweet.util.MD5Utils;
import com.wenwen.sweet.util.NumberUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserInfoServiceImpl.java
 *
 * @author ls
 *         <p/>
 *         描述：用户信息接口
 * @version 1.0
 * @date 2016-01-30
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceBase implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password))
            return null;

        String md5Password = MD5Utils.md5(password);
        UserInfo userInfo = userInfoMapper.selectByNameAndPassword(username, md5Password);
        return userInfo;
    }

    public UserInfo loginByOpenid(String openid) {
        if (StringUtils.isBlank(openid)) {
            return null;
        }
        UserInfo userInfo = userInfoMapper.selectByOpenid(openid);
        return userInfo;
    }

    public boolean loginCheck(String username, String password) {

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password))
            return false;

        String md5Password = MD5Utils.md5(password);
        int count = userInfoMapper.selectCountByNameAndPassword(username, md5Password);
        return count > 0;
    }

    public UserInfo getUserInfo(int id) {

        UserInfo userInfo = userInfoMapper.getUserInfoById(id);

        return userInfo;
    }

    public PagedResult<UserInfo> searchCommonUserByPage(Integer pageNum, Integer pageSize) {
        PagedResult<UserInfo> pagedResult = new PagedResult<UserInfo>(pageNum, pageSize);

        List<UserInfo> list = userInfoMapper.selectListByType(pagedResult.getOffset(), pagedResult.getLimit(), UserInfo.RoleType.COMMON_USER);
        if (list == null)
            list = Lists.newArrayList();
        pagedResult.setList(list);
        pagedResult.setTotal(userInfoMapper.countByType(UserInfo.RoleType.COMMON_USER));
        return pagedResult;
    }

    public void delete(int id) {

        if (id <= 0)
            return;
        int result = userInfoMapper.updateUserStatusById(id, UserInfo.Status.DELETE);
    }

    public int saveOrUpdate(UserInfo userInfo,boolean needUpdatePassWord) {

        if (userInfo == null)
            return 0;

        if (needUpdatePassWord && StringUtils.isNotBlank(userInfo.getPassword())) {
            userInfo.setPassword(MD5Utils.md5(userInfo.getPassword()));
        }

        if (NumberUtils.isPositive(userInfo.getId())) {
            return userInfoMapper.updateUserInfo(userInfo);
        } else {
            return userInfoMapper.insertUserInfo(userInfo);
        }
    }

    public boolean isUsernameExist(String username) {

        if (StringUtils.isBlank(username))
            return true;
        int count = userInfoMapper.selectCountByName(username);
        return count > 0;
    }

    public int resetPassword(int id) {
        if (id <= 0)
            return 0;

        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setPassword(MD5Utils.md5(UserInfo.DEFAULT_PASSWORD));

        return userInfoMapper.updateUserInfo(userInfo);
    }

    public List<UserInfo> getUserInfoByIds(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids))
            return Lists.newArrayList();

        List<UserInfo> result = userInfoMapper.getUserInfoByIds(ids);
        if (result == null)
            result = Lists.newArrayList();
        return result;
    }

    public UserInfo getUserInfoByOpenid(String openid) {
        if (StringUtils.isBlank(openid)) {
            return null;
        }
        return userInfoMapper.selectByOpenid(openid);
    }

    public int bindWeixin(int userId, String openid) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setOpenid(openid);
        return userInfoMapper.updateUserInfo(userInfo);
    }

    public int modifyPassword(int id, String password) {
        if (id <= 0)
            return 0;

        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setPassword(MD5Utils.md5(password));

        return userInfoMapper.updateUserInfo(userInfo);
    }
}
