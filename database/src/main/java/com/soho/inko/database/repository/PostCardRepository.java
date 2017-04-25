package com.soho.inko.database.repository;


import com.soho.inko.database.constant.PostCardProcessStatus;
import com.soho.inko.database.entity.PostCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * Created by ZhongChongtao on 2017/4/3.
 */
public interface PostCardRepository extends JpaRepository<PostCardEntity, String> {

    public PostCardEntity findTopByProcessStatusOrderByCreateAtAsc(PostCardProcessStatus processStatus);

}
