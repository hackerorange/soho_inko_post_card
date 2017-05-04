package com.soho.inko.web.response;

/**
 * Created by ZhongChongtao on 2017/4/28.
 */
public class StandardResponse extends AbstractResponse {
    /**
     * 成功标准响应
     */
    public void success() {
        this.setCode(200);
        this.setMessage("操作成功");
        this.setStatus("success");
        this.setKey("I000000");
    }

    /**
     * 失败标准响应
     */
    public void failure() {
        this.setCode(80);
        this.setMessage("操作失败");
        this.setStatus("failure");
        this.setKey("E000000");
    }

}
