package com.soho.inko.support;

import com.soho.inko.configuration.shiro.configuration.properties.ShiroPasswordProperties;
import com.soho.inko.database.entity.UserEntity;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by ZhongChongtao on 2017/3/18.
 */
@Component
public class PasswordSupport {
    private final ShiroPasswordProperties shiroPasswordProperties;

    @Autowired
    public PasswordSupport(ShiroPasswordProperties shiroPasswordProperties) {
        this.shiroPasswordProperties = shiroPasswordProperties;
    }

    /**
     * 对用户entity的密码进行加密
     *
     * @param userEntity 要加密的用户entity对象
     */
    public void encryptUserEntity(UserEntity userEntity) {
        userEntity.setSalt(UUID.randomUUID().toString());
        //运用算法进行加密
        String newPassword = new SimpleHash(
                shiroPasswordProperties.getAlgorithmName(),
                userEntity.getUserPassword(),
                ByteSource.Util.bytes(userEntity.getUserName() + userEntity.getSalt()),
                shiroPasswordProperties.getHashIterations()
        ).toHex();
        //将加密后的密码保存起来
        userEntity.setUserPassword(newPassword);
    }
}
