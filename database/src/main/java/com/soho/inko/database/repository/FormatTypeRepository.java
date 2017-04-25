package com.soho.inko.database.repository;


import com.soho.inko.database.entity.FormatTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ZhongChongtao on 2017/4/3.
 */
public interface FormatTypeRepository extends JpaRepository<FormatTypeEntity, String> {

    public FormatTypeEntity findByName(String name);
}
