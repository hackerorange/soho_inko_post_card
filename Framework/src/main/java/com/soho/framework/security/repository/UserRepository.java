package com.soho.framework.security.repository;

import com.soho.framework.security.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ZhongChongtao on 2017/3/18.
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {
    public UserEntity findByUserName(String userName);

    public UserEntity findByEmail(String email);
}
