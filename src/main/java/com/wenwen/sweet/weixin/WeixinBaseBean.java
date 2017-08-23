package com.wenwen.sweet.weixin;


import com.wenwen.sweet.commons.ToString;

/**
 * @author zhangyunxiang
 * @date 2016/2/21
 */
public class WeixinBaseBean extends ToString {
    private String errcode;
    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
