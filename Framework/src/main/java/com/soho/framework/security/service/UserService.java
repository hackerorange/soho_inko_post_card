package com.soho.framework.security.service;

import com.soho.framework.security.entity.UserEntity;

/**
 * Created by ZhongChongtao on 2017/3/18.
 */
public interface UserService {
    /**
     * 注册用户
     *
     * @param userName 用户名
     * @param password 用户密码
     * @param taoBaoId 用户淘宝ID
     * @param email    用户邮箱地址
     * @return 注册后的用户实体
     */
    public UserEntity regist(String userName, String password, String taoBaoId, String email);
}
