package com.soho.inko.database.repository;

import com.soho.inko.database.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ZhongChongtao on 2017/4/16.
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {
    public UserEntity findByTaoBaoId(String taobaoId);


    public UserEntity findByUserName(String userName);

    public UserEntity findByEmail(String email);
}
