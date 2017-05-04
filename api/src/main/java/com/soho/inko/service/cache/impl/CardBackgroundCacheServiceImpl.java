package com.soho.inko.service.cache.impl;

import com.soho.inko.database.constant.DictionaryParentEnum;
import com.soho.inko.database.entity.CardBackgroundEntity;
import com.soho.inko.database.entity.DictionaryEntity;
import com.soho.inko.database.repository.DictionaryRepository;
import com.soho.inko.service.cache.ICardBackgroundCacheService;
import com.soho.inko.utils.TypeChecker;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhongChongtao on 2017/4/26.
 */
@Service
@EnableCaching
public class CardBackgroundCacheServiceImpl implements ICardBackgroundCacheService {
    private final DictionaryRepository dictionaryRepository;


    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private ICardBackgroundCacheService cardBackgroundCacheService;

    @Autowired
    public CardBackgroundCacheServiceImpl(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    @Cacheable(cacheNames = "findAllAvailableCardBackgroundEntity", keyGenerator = "dateKeyGenerator")
    public List<CardBackgroundEntity> findAllAvailableCardBackgroundEntity() {
        List<CardBackgroundEntity> cardBackgroundEntities = new ArrayList<>();
        DictionaryEntity byDicName = dictionaryRepository.findByDicName(DictionaryParentEnum.NEGATIVE_SIDE.name());
        if (TypeChecker.isNull(byDicName)) {
            byDicName = new DictionaryEntity();
            byDicName.setHasChild(true);
            byDicName.setDicName(DictionaryParentEnum.NEGATIVE_SIDE.name());
            byDicName.setRemark(DictionaryParentEnum.NEGATIVE_SIDE.getDescription());
        }
        List<DictionaryEntity> allByParentId = dictionaryRepository.findAllByParentId(byDicName.getId());
        for (DictionaryEntity dictionaryEntity : allByParentId) {
            CardBackgroundEntity cardBackgroundEntityById = cardBackgroundCacheService.findCardBackgroundEntityByDictId(dictionaryEntity.getId());
            cardBackgroundEntities.add(cardBackgroundEntityById);
        }
        return cardBackgroundEntities;
    }

    @Override
    @Cacheable(cacheNames = "findCardBackgroundEntityByDictId", key = "#dictId")
    public CardBackgroundEntity findCardBackgroundEntityByDictId(String dictId) {
//        return backgroundRepository.findOne(dictId);
        CardBackgroundEntity cardBackgroundEntity = new CardBackgroundEntity();

        List<DictionaryEntity> allByDictionaryId = dictionaryRepository.findAllByParentId(dictId);
        for (DictionaryEntity paramMapEntity : allByDictionaryId) {
            try {
                String dictName = paramMapEntity.getDicName();
                String propertiesName = dictName.substring(dictName.lastIndexOf(":")+1);
                System.out.println("属性为" + propertiesName);
                BeanUtils.setProperty(cardBackgroundEntity, propertiesName, paramMapEntity.getDicValue());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return cardBackgroundEntity;
    }

    @Override
    @CacheEvict(cacheNames = "findAllAvailableCardBackgroundEntity", keyGenerator = "dateKeyGenerator")
    public void cleanAllCardBackCache() {
    }
}
