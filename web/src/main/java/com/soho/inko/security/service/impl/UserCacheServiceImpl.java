package com.soho.inko.security.service.impl;

import com.soho.inko.database.entity.UserEntity;
import com.soho.inko.database.repository.UserRepository;
import com.soho.inko.security.service.UserCacheService;
import com.soho.inko.support.PasswordSupport;
import com.soho.inko.utils.TypeChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by ZhongChongtao on 2017/3/18.
 */

@Service
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
public class UserCacheServiceImpl implements UserCacheService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordSupport passwordSupport;


    @Override
    @Cacheable(cacheNames = "user", key = "#userName")
    public UserEntity regist(String userName, String password, String taoBaoId, String email) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setUserPassword(password);
        userEntity.setTaoBaoId(taoBaoId);
        userEntity.setEmail(email);
        passwordSupport.encryptUserEntity(userEntity);
        userEntity = userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    @Cacheable(cacheNames = "user", key = "#userName")
    public UserEntity findByUserName(String userName) {
        UserEntity userEntity = userRepository.findByUserName(userName);
        if (TypeChecker.isNull(userEntity)) {
            logger.info("没有找到指定的用户");
        }
        return userEntity;
    }
}
