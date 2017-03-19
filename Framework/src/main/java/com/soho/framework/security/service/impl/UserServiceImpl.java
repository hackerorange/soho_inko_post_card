package com.soho.framework.security.service.impl;

import com.soho.framework.security.entity.UserEntity;
import com.soho.framework.security.repository.UserRepository;
import com.soho.framework.security.service.UserService;
import com.soho.framework.security.support.PasswordSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZhongChongtao on 2017/3/18.
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordSupport passwordSupport;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordSupport passwordSupport) {
        this.userRepository = userRepository;
        this.passwordSupport = passwordSupport;
    }

    @Override
    public UserEntity regist(String userName, String password, String taoBaoId, String email) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPassword(password);
        userEntity.setTaoBaoId(taoBaoId);
        userEntity.setEmail(email);
        passwordSupport.encryptUserEntity(userEntity);
        userEntity = userRepository.save(userEntity);
        return userEntity;
    }
}
