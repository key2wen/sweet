package com.wenwen.sweet.commons;

import java.util.Date;

/**
 * @date 2016年1月30日
 * @author yunxiang.zhang
 */
public class BaseBean extends ToString {
    private Integer id;
    private Date createTime;
    private Date updateTime;

    public BaseBean() {

    }

    public BaseBean(Date createTime, Date updateTime) {
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public BaseBean(Integer id, Date createTime, Date updateTime) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id ) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static void main(String[] args) {
        System.out.println(new BaseBean());
    }
}
