package com.soho.inko.service.impl;

import com.soho.inko.database.constant.PostCardProcessStatus;
import com.soho.inko.database.entity.PostCardEntity;
import com.soho.inko.database.repository.PostCardRepository;
import com.soho.inko.domain.PostCardInfoDTO;
import com.soho.inko.postcard.mapper.PostCardMapper;
import com.soho.inko.service.IPostCardCacheService;
import com.soho.inko.service.IPostCardService;
import com.soho.inko.utils.TypeChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by ZhongChongtao on 2017/4/3.
 */
@Service
public class PostCardServiceImpl implements IPostCardService {
    private final PostCardRepository postCardRepository;
    private final IPostCardCacheService postCardCacheService;
    private final PostCardMapper postCardMapper;

    @Autowired
    public PostCardServiceImpl(PostCardRepository postCardRepository, IPostCardCacheService postCardCacheService, PostCardMapper postCardMapper) {
        this.postCardRepository = postCardRepository;
        this.postCardCacheService = postCardCacheService;
        this.postCardMapper = postCardMapper;
    }

    @Override
    @Cacheable(cacheNames = "PostCardNeedCrop", key = "#tokenId")
    public PostCardEntity getNextPostCardNeedCrop(String tokenId) {
        //查找新创建状态的明信片实体
        PostCardEntity orderByCreateAtAsc = postCardRepository.findTopByProcessStatusOrderByCreateAtAsc(PostCardProcessStatus.CREATE_SUCCESS_1);
        //当前没有新创建的明信片
        if (TypeChecker.isNull(orderByCreateAtAsc)) {
            orderByCreateAtAsc = postCardRepository.findTopByProcessStatusOrderByCreateAtAsc(PostCardProcessStatus.BEFORE_CROP_2);
            return orderByCreateAtAsc;
        }
        orderByCreateAtAsc.setProcessStatus(PostCardProcessStatus.BEFORE_CROP_2);
        orderByCreateAtAsc.setCropBy(tokenId);
        postCardRepository.save(orderByCreateAtAsc);
        return orderByCreateAtAsc;
    }

    @Override
    @CacheEvict(cacheNames = "PostCardNeedCrop", key = "#tokenId")
    public PostCardEntity updatePostCardInfo(PostCardInfoDTO postCardInfoDTO, String tokenId) {
        PostCardEntity byId = postCardCacheService.findById(postCardInfoDTO.getPostCardId());
        Assert.notNull(byId, "没有找到记录");
        byId.setCropHeight(postCardInfoDTO.getCropInfo().getHeight());
        byId.setCropWidth(postCardInfoDTO.getCropInfo().getWidth());
        byId.setCropLeft(postCardInfoDTO.getCropInfo().getLeft());
        byId.setCropTop(postCardInfoDTO.getCropInfo().getTop());
        byId.setRotation(postCardInfoDTO.getCropInfo().getRotation());
        byId.setCropBy(tokenId);
        byId.setProcessStatus(PostCardProcessStatus.AFTER_CROP_3);
        return postCardCacheService.updatePostCardEntity(byId);
    }

    @Override
    public boolean deletePostCardInfoByOrderId(String orderId) {
        postCardMapper.deleteAllByPostCardNumber(orderId);
//        postCardRepository.deleteByOrderId(orderId);
        return true;
    }


}
