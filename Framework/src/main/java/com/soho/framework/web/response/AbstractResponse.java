package com.soho.framework.web.response;

/**
 * Created by ZhongChongtao on 2017/2/12.
 */
public abstract class AbstractResponse {
    private int code = 200;
    private String message;
    private String status;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

