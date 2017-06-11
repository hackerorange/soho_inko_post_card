package com.soho.inko.service.impl;

/**
 * Created by ZhongChongtao on 2017/5/20.
 */
public class NoSuchOrderException extends Exception {

    public NoSuchOrderException(String orderId) {
        super("没有找到订单信息，订单号为：" + orderId);
    }
}
