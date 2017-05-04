package com.soho.inko.service;


import com.soho.inko.database.entity.PostCardEntity;
import com.soho.inko.domain.PostCardInfoDTO;

import java.util.List;

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

    /**
     * 更新明信片信息
     *
     * @param postCardInfoDTO 明信片信息
     * @param tokenId         更新用户ID
     * @return 更新后的明信片信息
     */
    public PostCardEntity updatePostCardInfo(PostCardInfoDTO postCardInfoDTO, String tokenId);

    /**
     * 根据订单ID，删除所有与之关联的明信片
     *
     * @param orderId 订单ID
     * @return 是否删除成功
     */
    boolean deletePostCardInfoByOrderId(String orderId);


    /**
     * 添加一个新的明信片到指定的信封
     *
     * @param envelopeId 信封ID
     * @param fileId     文件ID
     * @return 创建的PostCard
     */
    public PostCardEntity createNewPostCard(String envelopeId, String fileId);

    /**
     * 根据信封ID，获取所有的明信片
     *
     * @param envelopeId 信封ID
     * @return 此信封下对应的所有的明信片
     */
    public List<PostCardEntity> findAllByEnvelopeId(String envelopeId);

    /**
     * 根据postCardId,删除明信片
     *
     * @param postCardId 明信片ID
     * @return 是否删除成功
     */
    public boolean deletePostCard(String postCardId);

}
