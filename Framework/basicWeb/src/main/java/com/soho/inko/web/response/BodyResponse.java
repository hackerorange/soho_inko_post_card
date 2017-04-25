package com.soho.inko.web.response;

/**
 * Created by ZhongChongtao on 2017/2/13.
 */
public class BodyResponse<T> extends AbstractResponse {
    private T body;

    public BodyResponse() {
    }

    public BodyResponse(T body) {
        this.body = body;
    }

    public static <T> BodyResponse<T> newSuccessInstance(Class<T> tClass) {
        BodyResponse<T> bodyResponse = new BodyResponse<>();
        bodyResponse.setMessage("操作成功");
        bodyResponse.setCode(200);
        bodyResponse.setStatus("T");
        bodyResponse.setKey("I00000");
        return bodyResponse;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;

    }
}

