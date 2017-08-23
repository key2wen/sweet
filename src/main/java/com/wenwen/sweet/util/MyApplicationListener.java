package com.wenwen.sweet.util;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * 监听spring容器加载完成事件
 * @author yunxiangzyx
 * @date 16/4/11.
 */
@Service
public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {


    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() != null) {
            LoggerUtil.info("################# application started! ###############");
        }
    }
}
