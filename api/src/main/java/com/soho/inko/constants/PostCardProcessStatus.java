package com.soho.inko.constants;

/**
 * Created by ZhongChongtao on 2017/4/3.
 */
public enum PostCardProcessStatus {
    //明信片创建成功时候的状态
    CREATE_SUCCESS,
    //明信片裁切操作之前的状态
    BEFORE_CROP,
    //明信片裁切操作之后的状态
    AFTER_CROP,
    //明信片处理之前的状态
    BEFORE_PROCESS,
    //明信片处理之后的状态
    AFTER_PROCESS,;
}
