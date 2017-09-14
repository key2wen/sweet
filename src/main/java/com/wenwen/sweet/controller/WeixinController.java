package com.wenwen.sweet.controller;

import com.wenwen.sweet.util.SignUtil;
import com.wenwen.sweet.weixin.AuthorizationCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangyunxiang
 * @date 2016/2/21
 */
@Controller
@RequestMapping("/weixin")
public class WeixinController {

    Logger logger = LoggerFactory.getLogger(WeixinController.class);

    private static final String AUTH2_GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type=authorization_code";

    @Autowired
    RestTemplate restTemplate;

    public String getAuth2GetAccessTokenUrl(String code) {
        return AUTH2_GET_ACCESS_TOKEN_URL.replace("{appid}", "wx918c0bd4994db440")
                .replace("{secret}", "4643ffcb1de7f7e9eca755ef236afa7d")
                .replace("{code}", code);
    }

    /**
     *
     */
    @ResponseBody
    @RequestMapping("/first")
    public String first(HttpServletRequest request) {

        //微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp，nonce参数
        String signature = request.getParameter("signature");
        //时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");

        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            logger.info("[signature: " + signature + "]<-->[timestamp: " + timestamp + "]<-->[nonce: " + nonce + "]<-->[echostr: " + echostr + "]");
            return echostr;
        } else {
            return "";
        }

    }

    /**
     *
     */
    @ResponseBody
    @RequestMapping("/test")
    public String test(String code, String state) {
        String url = getAuth2GetAccessTokenUrl(code);
        ResponseEntity<AuthorizationCode> responseEntity = restTemplate.getForEntity(url, AuthorizationCode.class);
        AuthorizationCode authorizationCode = responseEntity.getBody();
        String openid = authorizationCode.getOpenid();

        return "success";
    }
}
