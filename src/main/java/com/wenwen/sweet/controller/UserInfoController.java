package com.wenwen.sweet.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wenwen.sweet.auth.Auth;
import com.wenwen.sweet.commons.JsonWrapper;
import com.wenwen.sweet.model.UserInfo;
import com.wenwen.sweet.service.UserInfoService;
import com.wenwen.sweet.util.MD5Utils;
import com.wenwen.sweet.util.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * UserInfoController.java
 *
 * @author ls
 * @version 1.0
 * @date 2016-01-30
 */
@Controller
@RequestMapping("/user")
@SessionAttributes("currUser")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 用户登录, 限制只能管理员账号可以登录
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public JsonWrapper<?> login(String username, String password, HttpSession session) {

        UserInfo userInfo = userInfoService.login(username, password);
        if (userInfo != null) {
            session.setAttribute("currUser", userInfo);
            session.setMaxInactiveInterval(60 * 60 * 2);
            return new JsonWrapper<Object>();
        }
        return JsonWrapper.buildFailedResult("用户名或密码错误");
    }

    /**
     * 退出登录
     *
     * @param username
     * @return
     */
    @RequestMapping("logout")
    public ModelAndView logout(String username, SessionStatus status, WebRequest request) {
        status.setComplete();
        request.removeAttribute("currUser", WebRequest.SCOPE_SESSION);
        ModelMap model = new ModelMap();
        model.addAttribute("msg", "退出登录");

        return new ModelAndView("login", model);
    }


    /**
     * 删除用户信息
     *
     * @param id
     * @return
     */
    @RequestMapping("delete")
    public String delete(Integer id) {

        String viewName = "redirect:/user/commonList";
        if (NumberUtils.isPositive(id))
            userInfoService.delete(id);
        return viewName;
    }

    /**
     * 新增或编辑用户
     */
    @RequestMapping("addOrEdit")
    public ModelAndView addOrEdit(Integer id, UserInfo userInfo) {
        String viewName = "redirect:/user/commonList";

        //新增时，如果用户名为空，则直接返回
        if (userInfo == null || (!NumberUtils.isPositive(userInfo.getId()) && StringUtils.isBlank(userInfo.getUsername())))
            return new ModelAndView(viewName);

        userInfo.setRoleType(UserInfo.RoleType.COMMON_USER);
        if (NumberUtils.isPositive(id)) {
            userInfo.setId(id);
        } else {
            boolean flag = userInfoService.isUsernameExist(userInfo.getUsername());
            if (flag)
                return new ModelAndView(viewName, "msg", "添加失败，用户名已存在");
            userInfo.setPassword(MD5Utils.md5(UserInfo.DEFAULT_PASSWORD));
        }

        userInfoService.saveOrUpdate(userInfo, true);

        return new ModelAndView(viewName);
    }

    /**
     * 校验用户名是否已经存在
     *
     * @param username
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkUsername")
    public JsonWrapper checkUsername(String username) {

        if (StringUtils.isBlank(username))
            return JsonWrapper.buildFailedResult("用户名不能为空");

        boolean flag = userInfoService.isUsernameExist(username);
        if (flag)
            return JsonWrapper.buildFailedResult("用户名已存在");
        return new JsonWrapper();
    }

    /**
     * 重置用户密码
     *
     * @param id
     * @return
     */
    @RequestMapping("resetPassword")
    public ModelAndView resetPassword(Integer id) {
        String viewName = "redirect:/user/commonList";
        if (NumberUtils.isPositive(id)) {
            int result = userInfoService.resetPassword(id);
            String msg = result > 0 ? "重置成功" : "重置失败";
            return new ModelAndView(viewName, "msg", msg);
        }
        return new ModelAndView(viewName);
    }

    @ResponseBody
    @RequestMapping(value = "register.json", method = RequestMethod.POST)
    public JsonWrapper<?> register(@Valid UserInfo userInfo, BindingResult result, ModelMap map) {
        String errMsg = null;
        if (result.hasErrors()) {
            errMsg = result.getFieldError().getDefaultMessage();
            return JsonWrapper.buildFailedResult(errMsg);
        }
        boolean flag = userInfoService.isUsernameExist(userInfo.getUsername());
        if (flag) {
            return JsonWrapper.buildFailedResult("用户名:" + userInfo.getUsername() + " 已存在");
        }
        userInfo.setRoleType(UserInfo.RoleType.NON_PAYMENT_USER);
        int ret = userInfoService.saveOrUpdate(userInfo, true);
        if (ret == 0) {
            return JsonWrapper.buildFailedResult("用户注册失败");
        }
        map.put("currUser", userInfo);
        return JsonWrapper.buildSuccessResult();
    }


    @ResponseBody
    @RequestMapping("canBind.json")
    @Auth(UserInfo.RoleType.NON_PAYMENT_USER)
    public JsonWrapper<?> canBind(HttpSession session) {
        UserInfo currUser = (UserInfo) session.getAttribute("currUser");
        if (currUser != null) {
            // 如果当前用户已经没有绑定过openid,并且session中有openid,则可以绑定
            if (StringUtils.isBlank(currUser.getOpenid())) {
                String sessionOpenid = (String) session.getAttribute("openid");
                if (userInfoService.getUserInfoByOpenid(sessionOpenid) != null) {
                    return JsonWrapper.buildFailedResult("当前微信号已绑定");
                }
                if (StringUtils.isNotBlank(sessionOpenid)) {
                    return JsonWrapper.buildSuccessResult();
                } else {
                    return JsonWrapper.buildFailedResult("无法识别微信");
                }
            } else {
                return JsonWrapper.buildFailedResult("当前用户已绑定");
            }
        } else {
            return JsonWrapper.buildFailedResult("未登录");
        }
    }

    @ResponseBody
    @RequestMapping(value = "bindWeixin.json", method = RequestMethod.POST)
    @Auth(UserInfo.RoleType.NON_PAYMENT_USER)
    public JsonWrapper<?> bindWeixin(HttpSession session) {
        UserInfo currUser = (UserInfo) session.getAttribute("currUser");
        currUser = userInfoService.getUserInfo(currUser.getId());
        if (currUser != null) {
            // 如果当前用户已经没有绑定过openid,并且session中有openid,则可以绑定
            if (StringUtils.isBlank(currUser.getOpenid())) {
                String sessionOpenid = (String) session.getAttribute("openid");
                if (StringUtils.isNotBlank(sessionOpenid)) {
                    int ret = userInfoService.bindWeixin(currUser.getId(), sessionOpenid);
                    if (ret > 0) {
                        return JsonWrapper.buildSuccessResult("绑定成功");
                    } else {
                        return JsonWrapper.buildFailedResult("绑定失败");
                    }
                } else {
                    return JsonWrapper.buildFailedResult("无法识别微信");
                }
            } else {
                return JsonWrapper.buildFailedResult("已绑定");
            }
        } else {
            return JsonWrapper.buildFailedResult("未登录");
        }
    }
}
