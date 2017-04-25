package com.soho.inko.service;

import com.soho.inko.database.entity.PostCardEntity;

/**
 * Created by ZhongChongtao on 2017/4/3.
 */
public interface IPostCardCacheService {
    /**
     * 根据ID,查找postCardEntity
     *
     * @param id postCardId
     * @return 查找到的postCardEntity
     */
    public PostCardEntity findById(String id);

    /**
     * 更新postCard信息
     *
     * @return 更新后的postCard
     */
    public PostCardEntity updatePostCardEntity(PostCardEntity postCardEntity);


}
