package com.soho.framework.web.response;

/**
 * Created by ZhongChongtao on 2017/2/13.
 */
public class BodyResponse<T> extends AbstractResponse {
    private T body;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}

