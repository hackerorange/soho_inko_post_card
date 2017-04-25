package com.soho.inko.service.impl;

import com.soho.inko.database.entity.PostCardEntity;
import com.soho.inko.database.repository.PostCardRepository;
import com.soho.inko.service.IPostCardCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZhongChongtao on 2017/4/3.
 */
@Service
public class PostCardCacheServiceImpl implements IPostCardCacheService {
    private final PostCardRepository postCardRepository;

    @Autowired
    public PostCardCacheServiceImpl(PostCardRepository postCardRepository) {
        this.postCardRepository = postCardRepository;
    }

    @Override
    public PostCardEntity findById(String id) {
        return postCardRepository.findOne(id);
    }

    @Override
    public PostCardEntity updatePostCardEntity(PostCardEntity postCardEntity) {
        postCardEntity = postCardRepository.save(postCardEntity);
        return postCardEntity;
    }
}
