package com.soho.inko.service;


import com.soho.inko.database.entity.PostCardEntity;
import com.soho.inko.domain.PostCardInfoDTO;

/**
 * Created by ZhongChongtao on 2017/4/3.
 */
public interface IPostCardService {
    /**
     * 获取下一个需要裁切的明信片
     *
     * @param tokenId 用户tokenId
     * @return 查找到的明信片
     */
    public PostCardEntity getNextPostCardNeedCrop(String tokenId);


    public PostCardEntity updatePostCardInfo(PostCardInfoDTO postCardInfoDTO, String tokenId);

    boolean deletePostCardInfoByOrderId(String orderId);
}
