package com.soho.inko.database.repository;


import com.soho.inko.database.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ZhongChongtao on 2017/3/11.
 */
public interface FileRepository extends JpaRepository<FileEntity, String> {}

