/*
 * 文 件 名:  FileController.java
 * 版    权:  Copyright © 2016- 杭州市XX科技有限公司 All Rights Reserved
 * 编写人:  yangyong
 * 编 写 时 间:  2016-1-31
 */
package com.wenwen.sweet.controller;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * 文件上传控制器
 * 
 * @author yangyong
 * @since 2016-1-31
 */
@Controller
@RequestMapping("/file")
public class FileController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    /**
     * 上传文件
     * 
     * @param upload
     * @param request
     */
    @RequestMapping(value = "/upload")
    public void upload(MultipartFile upload, HttpServletRequest request) {
        // String uploadContentType = upload.getContentType();
        // TODO 文件校验
        String uploadFileName = System.currentTimeMillis() + upload.getOriginalFilename();
        // 可配置
        final String dir = request.getSession().getServletContext().getRealPath("/uploadfile");
        File file = new File(dir + File.separator + uploadFileName);
        try {
            FileUtils.copyInputStreamToFile(upload.getInputStream(), file);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            // TODO 异常处理
        }
        // TODO 结果返回
    }
}
