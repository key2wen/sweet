package com.wenwen.sweet.controller.weixin;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 回复消息之图文消息
 *
 * @author 32950745
 */
@XStreamAlias("NewsMessage ")
public class NewsMessage extends BaseMessage {

    // 回复的消息内容
    @XStreamAlias("Content")
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}