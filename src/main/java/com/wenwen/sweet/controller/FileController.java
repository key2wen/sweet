/*
 * 文 件 名:  FileController.java
 * 版    权:  Copyright © 2016- 杭州市XX科技有限公司 All Rights Reserved
 * 编写人:  yangyong
 * 编 写 时 间:  2016-1-31
 */
package com.wenwen.sweet.controller;

import com.alibaba.fastjson.JSONObject;
import com.wenwen.sweet.model.Word;
import com.wenwen.sweet.modelao.WordAO;
import com.wenwen.sweet.service.WordService;
import com.wenwen.sweet.util.JSONUtil;
import com.wenwen.sweet.util2.JsonUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Iterator;

/**
 * 文件上传控制器
 *
 * @author yangyong
 * @since 2016-1-31
 */
@Controller
@SessionAttributes("currUser")
@RequestMapping("/file")
public class FileController {

    @Autowired
    private WordService wordService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    /**
     * 上传文件
     *
     * @param request
     */
    @RequestMapping(value = "/upload/word")
    public ModelAndView upload(HttpServletRequest request, WordAO wordAO) throws Exception {

        // 可配置
//        final String dir = request.getSession().getServletContext().getRealPath("/uploadfile/word");
        final String dir = "/sweet/upload/word";

//        wordAO = JsonUtil.getObjectFromRequest(request, WordAO.class);
//        if (wordAO == null) {
//            return null;
//        }
        Word word = new Word();

        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                //一次遍历所有文件
                String paraName = iter.next().toString();
                MultipartFile pFile = multiRequest.getFile(paraName);

                if (pFile != null) {
                    String filePath = dir + File.separator + pFile.getOriginalFilename();
                    File file = new File(filePath);
                    //上传
                    pFile.transferTo(file);

                    BeanUtils.setProperty(word, paraName, filePath);
                }
            }
        }

        word = wordAO.buildWord(word);
        int res = wordService.saveWord(word);

        return new ModelAndView("redirect:/word/list");

    }
}
