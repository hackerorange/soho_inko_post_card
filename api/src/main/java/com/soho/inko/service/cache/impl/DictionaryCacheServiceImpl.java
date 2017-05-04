package com.soho.inko.service.cache.impl;

import com.soho.inko.Mapper.DictionaryMapper;
import com.soho.inko.database.entity.DictionaryEntity;
import com.soho.inko.database.repository.DictionaryRepository;
import com.soho.inko.service.cache.IDictionaryCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

/**
 * Created by ZhongChongtao on 2017/4/28.
 */
@EnableCaching
@Service
public class DictionaryCacheServiceImpl implements IDictionaryCacheService {
    private final DictionaryRepository dictionaryRepository;
    private final DictionaryMapper dictionaryMapper;

    @Autowired
    public DictionaryCacheServiceImpl(DictionaryRepository dictionaryRepository, DictionaryMapper dictionaryMapper) {
        this.dictionaryRepository = dictionaryRepository;
        this.dictionaryMapper = dictionaryMapper;
    }

    @Override
    @Cacheable(cacheNames = "getDictionaryById", key = "#dictName")
    public DictionaryEntity getDictionaryById(String dictName) {
        return dictionaryRepository.findByDicName(dictName);
    }

    @Override
    @CacheEvict(cacheNames = "getDictionaryById", key = "#dictName")
    public void removeDictionaryCache(String dictName) {
        dictionaryMapper.deleteDictionaryByDictName(dictName);
    }
}
