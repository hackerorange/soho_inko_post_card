package com.soho.inko.database.repository;


import com.soho.inko.database.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ZhongChongtao on 2017/3/29.
 */
public interface SizeRepository extends JpaRepository<SizeEntity, String> {

    public SizeEntity findByName(String name);

}
