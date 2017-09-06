package com.wenwen.sweet.controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wenwen.sweet.model.MwordAffix;
import com.wenwen.sweet.service.MwordAffixService;
import com.wenwen.sweet.util.SweetBusinessException;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("currUser")
@RequestMapping("/qrcode")
public class QRCodeController {

    static String baiduPre = "http://fanyi.baidu.com/#en/zh/";
    static String youdaoPre = "http://youdao.com/w/";
    static String youdaoAft = "/#keyfrom=dict2.top";

    static String systemPre = "http://localhost:8080/word/mword/";

    static String shengmuPre = "http://223.whvip.com/html/shengmu/";

    static Map<String, String> shengmuMap = new HashMap<String, String>();

    static {
        shengmuMap.put("b", "23.html");
        shengmuMap.put("p", "22.html");
        shengmuMap.put("m", "21.html");
        shengmuMap.put("f", "20.html");
        shengmuMap.put("d", "19.html");
        shengmuMap.put("t", "18.html");
        shengmuMap.put("n", "17.html");

        shengmuMap.put("l", "16.html");
        shengmuMap.put("g", "15.html");
        shengmuMap.put("k", "14.html");
        shengmuMap.put("h", "13.html");
        shengmuMap.put("j", "12.html");

        shengmuMap.put("q", "11.html");
        shengmuMap.put("x", "10.html");
        shengmuMap.put("zh", "9.html");
        shengmuMap.put("ch", "8.html");
        shengmuMap.put("sh", "7.html");
        shengmuMap.put("r", "6.html");

        shengmuMap.put("z", "5.html");
        shengmuMap.put("c", "4.html");
        shengmuMap.put("s", "3.html");
        shengmuMap.put("y", "2.html");
        shengmuMap.put("w", "1.html");
    }

    @Autowired
    private MwordAffixService mwordAffixService;

    static String BaiduFY = "baidu", YouDaoFY = "youdao", ShengMuFY = "shengmu", SystemFY = "system";

    Logger logger = LoggerFactory.getLogger(QRCodeController.class);

    @RequestMapping("/pic")
    public void getQRCodePic(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

        String qrtext = request.getParameter("qrtext");

        if (StringUtils.isBlank(qrtext)) {
            return;
        }
        String fanyi = request.getParameter("fanyi");
        if (StringUtils.isBlank(fanyi)) {
            return;
        }
        logger.info("开始生成：value：{},fanyi:{}", qrtext, fanyi);

        //先默认查普通单词
        String realQrValue = getValue(qrtext, fanyi, 1);

        ByteArrayOutputStream out = QRCode.from(realQrValue).to(ImageType.PNG).stream();

        response.setContentType("image/png");
        response.setContentLength(out.size());

        OutputStream outStream = response.getOutputStream();

        outStream.write(out.toByteArray());

        outStream.flush();
        outStream.close();
    }


    private String getValue(String qrtext, String dict, Integer type) {

        qrtext = qrtext.trim();

        MwordAffix mwordAffix = new MwordAffix();
        mwordAffix.setDict(dict);
        mwordAffix.setType(type);
        List<MwordAffix> list = mwordAffixService.searchMwordAffixs(mwordAffix);
        if (CollectionUtils.isEmpty(list)) {
            throw new SweetBusinessException("词典不存在,type=" + type + ",dict=" + dict);
        }
        MwordAffix one = list.get(0);

        if (dict.equals(ShengMuFY)) {
            qrtext = shengmuMap.get(qrtext);
        }

        return (one.getWordPrefix()==null?"":one.getWordPrefix())
                + qrtext
                + (one.getWordSuffix() == null ? "" : one.getWordSuffix());
    }

    private String getValue(String qrtext, String fanyi) {


        qrtext = qrtext.trim();

        if (SystemFY.equals(fanyi)) {
            return systemPre + qrtext;
        } else if (BaiduFY.equals(fanyi)) {
            return baiduPre + qrtext;
        } else if (YouDaoFY.equals(fanyi)) {
            return youdaoPre + qrtext + youdaoAft;
        } else if (ShengMuFY.equals(fanyi)) {
            return shengmuPre + shengmuMap.get(qrtext);
        } else {
            return qrtext;
        }
    }
}