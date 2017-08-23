package com.wenwen.sweet.util;

import com.wenwen.sweet.weixin.AuthorizationCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * Created by zyx on 16-2-28.
 */
//@Component
public class WeixinUtil {

    private static final Logger logger = LoggerFactory.getLogger(WeixinUtil.class);

    private static WeixinUtil weixinUtil;

    private static final String AUTH2_GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={appsecret}&code={code}&grant_type=authorization_code";

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        weixinUtil = this;
        weixinUtil.restTemplate = this.restTemplate;
    }

    public static AuthorizationCode getAuthorizationCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        String url = AUTH2_GET_ACCESS_TOKEN_URL
//            .replace("{appid}", ConfigUtil.get("appid"))
//            .replace("{appsecret}", ConfigUtil.get("appsecret"))
            .replace("{code}", code);

        AuthorizationCode authorizationCode = weixinUtil.restTemplate.getForObject(url, AuthorizationCode.class);
        if (logger.isDebugEnabled()) {
            logger.info("call: " + url + " return: " + authorizationCode);
        }
        return authorizationCode;
    }


}
