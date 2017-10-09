package com.wenwen.sweet.controller;

import com.wenwen.sweet.controller.weixin.CoreService;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhangyunxiang
 * @date 2016/2/21
 */
@Controller
@RequestMapping("/weixin")
public class WeixinController {

    Logger logger = LoggerFactory.getLogger(WeixinController.class);

    private static final String AUTH2_GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type=authorization_code";


    private static final String AUTH_GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=％s&appid=%s&secret=%s";

    @Autowired
    RestTemplate restTemplate;

    public String getAuth2GetAccessTokenUrl(String code) {
        return AUTH2_GET_ACCESS_TOKEN_URL.replace("{appid}", "wx918c0bd4994db440")
                .replace("{secret}", "4643ffcb1de7f7e9eca755ef236afa7d")
                .replace("{code}", code);
    }

    public String getAuthGetAccessTokenUrl(String code) {
        return String.format(AUTH_GET_ACCESS_TOKEN_URL, "client_credential", "wx90c293a362674ebf", "5df92836c85b7e5d95eeccabbc413c75");
    }

    /**
     *
     */
    @ResponseBody
    @RequestMapping("/first")
    public void first(HttpServletRequest request, HttpServletResponse response) throws Exception {

        boolean isGet = request.getMethod().toLowerCase().equals("get");

        //调试
        if (isGet) {

            firstBindServerUrl(request, response);
        } else {

            response.setCharacterEncoding("UTF-8");
            PrintWriter print = response.getWriter();

            // 进入post聊天处理
            System.out.println("enter post");

            // 接收消息并返回消息
            // 调用核心服务类接收处理请求
            String respXml = CoreService.processRequest(request);
            print.print(respXml);
            print.flush();
            print.close();
        }

    }

    private void firstBindServerUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp，nonce参数
        String signature = request.getParameter("signature");
        //时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();

        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                logger.info("[signature: " + signature + "]<-->[timestamp: " + timestamp + "]<-->[nonce: " + nonce + "]<-->[echostr: " + echostr + "]");
                out.write(echostr);
                out.flush();
            }
        } finally {
            out.close();
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
