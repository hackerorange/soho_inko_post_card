package com.soho.inko.database.repository;

import com.soho.inko.database.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ZhongChongtao on 2017/5/20.
 */
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, String> {
}
