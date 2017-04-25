package com.soho.inko.security.service;


import com.soho.inko.database.entity.UserEntity;

/**
 * Created by ZhongChongtao on 2017/3/18.
 */
public interface UserCacheService {
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

    /**
     * 根据用户名称，查找用户
     *
     * @param userName 用户名称
     * @return 查找到的用户
     */
    public UserEntity findByUserName(String userName);
}
