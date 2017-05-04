package com.soho.inko.service;

import com.soho.inko.database.entity.CardBackgroundEntity;

/**
 * Created by ZhongChongtao on 2017/4/26.
 */
public interface ICardBackgroundService {
    /**
     * 创建新的明信片反面
     *
     * @param title   反面名称
     * @param fileId  pdf文件ID
     * @param tokenId 创建这个TokenId
     * @return 创建的明信片对象
     */
    CardBackgroundEntity createBackground(String title, String fileId, String tokenId);

    /**
     * 删除明信片反面
     *
     * @param title 反面Title
     * @return 是否删除成功
     */
    boolean deleteBackground(String title);


}

