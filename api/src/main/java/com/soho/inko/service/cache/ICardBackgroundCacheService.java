package com.soho.inko.service.cache;

import com.soho.inko.database.entity.CardBackgroundEntity;

import java.util.List;

/**
 * Created by ZhongChongtao on 2017/4/26.
 */
public interface ICardBackgroundCacheService {

    /**
     * 获取所有可用的样式,缓存到redis中，每隔1天更新一次
     *
     * @return 所有可用的明信片反面样式
     */
    List<CardBackgroundEntity> findAllAvailableCardBackgroundEntity();

    /**
     * 根据反面ID，获取反面信息
     *
     * @param backgroundId 反面ID
     * @return 反面信息
     */
    CardBackgroundEntity findCardBackgroundEntityByDictId(String backgroundId);

    /**
     * 清理当天的缓存
     */
    void cleanAllCardBackCache();
}
