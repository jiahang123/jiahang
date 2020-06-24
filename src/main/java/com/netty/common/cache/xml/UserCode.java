package com.netty.common.cache.xml;

/**
 * @version: 1.0.0
 * @Description:  读取 userCode.xml 文件
 * @author: H 
 * @date: 2019-10-29 10:43:46
 */
public class UserCode {
    private String code;// 错误码 配置文件实体
    private String msgZh; // 中文信息
    private String msgEn;// 英文信息

    public UserCode() {
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsgZh() {
        return this.msgZh;
    }

    public void setMsgZh(String msgZh) {
        this.msgZh = msgZh;
    }

    public String getMsgEn() {
        return this.msgEn;
    }

    public void setMsgEn(String msgEn) {
        this.msgEn = msgEn;
    }
}
