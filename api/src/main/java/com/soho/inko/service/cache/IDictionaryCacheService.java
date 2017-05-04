package com.soho.inko.service.cache;

import com.soho.inko.database.entity.DictionaryEntity;

/**
 * Created by ZhongChongtao on 2017/4/28.
 */
public interface IDictionaryCacheService {
    /**
     * 根据ID获取字典
     *
     * @param dictId 字典ID
     * @return 查找到的字典
     */
    public DictionaryEntity getDictionaryById(String dictId);

    /**
     * 从缓存中移除字典，并且删除字典及其子对象
     *
     * @param dictId 字典ID
     */
    public void removeDictionaryCache(String dictId);
}
