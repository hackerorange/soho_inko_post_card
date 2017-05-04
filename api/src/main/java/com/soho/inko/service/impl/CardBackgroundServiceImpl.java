package com.soho.inko.service.impl;

import com.soho.inko.database.constant.DictionaryParentEnum;
import com.soho.inko.database.entity.CardBackgroundEntity;
import com.soho.inko.database.entity.DictionaryEntity;
import com.soho.inko.database.repository.DictionaryParamMapRepository;
import com.soho.inko.database.repository.DictionaryRepository;
import com.soho.inko.service.ICardBackgroundService;
import com.soho.inko.service.cache.ICardBackgroundCacheService;
import com.soho.inko.service.cache.IDictionaryCacheService;
import com.soho.inko.utils.TypeChecker;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;


/**
 * Created by ZhongChongtao on 2017/4/26.
 */
@Service
public class CardBackgroundServiceImpl implements ICardBackgroundService {
    private final DictionaryRepository dictionaryRepository;
    private final IDictionaryCacheService dictionaryCacheService;
    private final ICardBackgroundCacheService cardBackgroundCacheService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    public CardBackgroundServiceImpl(DictionaryRepository dictionaryRepository, DictionaryParamMapRepository dictionaryParamMapRepository, ICardBackgroundCacheService cardBackgroundCacheService, IDictionaryCacheService dictionaryCacheService) {
        this.dictionaryRepository = dictionaryRepository;
        this.cardBackgroundCacheService = cardBackgroundCacheService;
        this.dictionaryCacheService = dictionaryCacheService;
    }

    @Override
    public CardBackgroundEntity createBackground(String title, String fileId, String tokenId) {
        CardBackgroundEntity backgroundEntity = new CardBackgroundEntity();
        backgroundEntity.setFileId(fileId);
        backgroundEntity.setTitle(title);
        DictionaryEntity nativeSizeDictionary = dictionaryRepository.findByDicName(DictionaryParentEnum.NEGATIVE_SIDE.name());
        //如果字典里没有这个键值，进行初始化  明信片反面
        if (TypeChecker.isNull(nativeSizeDictionary)) {
            nativeSizeDictionary = new DictionaryEntity();
            nativeSizeDictionary.setHasChild(true);
            nativeSizeDictionary.setRemark(DictionaryParentEnum.NEGATIVE_SIDE.getDescription());
            nativeSizeDictionary.setDicName(DictionaryParentEnum.NEGATIVE_SIDE.name());
            //保存根目录
            dictionaryRepository.save(nativeSizeDictionary);
        }
        DictionaryEntity nativeSizeDictionaryNew = new DictionaryEntity();
        nativeSizeDictionaryNew.setDicName(DictionaryParentEnum.NEGATIVE_SIDE.name() + ":" + title);
        nativeSizeDictionaryNew.setHasChild(false);
        nativeSizeDictionaryNew.setParentId(nativeSizeDictionary.getId());
        if (TypeChecker.isNull(dictionaryRepository.findByDicName(nativeSizeDictionaryNew.getDicName()))) {
            DictionaryEntity save = dictionaryRepository.save(nativeSizeDictionaryNew);
            DictionaryEntity paramMapEntity;

            try {
                Map<String, String> describe = BeanUtils.describe(backgroundEntity);
                Set<Map.Entry<String, String>> entries = describe.entrySet();
                for (Map.Entry<String, String> entry : entries) {
                    if (entry.getKey().equalsIgnoreCase("class")) {
                        continue;
                    }
                    paramMapEntity = new DictionaryEntity();
                    paramMapEntity.setDicName(save.getDicName() + ":" + entry.getKey());
                    paramMapEntity.setDicValue(entry.getValue());
                    paramMapEntity.setParentId(save.getId());
                    dictionaryRepository.save(paramMapEntity);
                }
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            logger.info("字典保存完毕");
            //清理缓存
            cardBackgroundCacheService.cleanAllCardBackCache();
        } else {
            logger.info("字典中已经有此对象");
        }


//        backgroundEntity.setFileId(fileId);
//        backgroundEntity.setCreateUserId(tokenId);
//        backgroundEntity.setTitle(title);
//        backgroundEntity.setVisible(true);

        return backgroundEntity;
    }

    @Override
    public boolean deleteBackground(String title) {
        DictionaryEntity nativeSizeDictionary = dictionaryRepository.findByDicName(DictionaryParentEnum.NEGATIVE_SIDE.name());
        if (TypeChecker.isNull(nativeSizeDictionary)) {
            return false;
        }
        DictionaryEntity nativeSizeDictionaryNew = new DictionaryEntity();
        nativeSizeDictionaryNew.setDicName(DictionaryParentEnum.NEGATIVE_SIDE.name() + ":" + title);
        nativeSizeDictionaryNew.setHasChild(false);
        nativeSizeDictionaryNew.setParentId(nativeSizeDictionary.getId());
        if (TypeChecker.isNull(dictionaryRepository.findByDicName(nativeSizeDictionaryNew.getDicName()))) {
            logger.info("没有找到指定ID的background");
            return false;
        }
        dictionaryCacheService.removeDictionaryCache(nativeSizeDictionaryNew.getDicName());
        logger.info("删除成功");
        cardBackgroundCacheService.cleanAllCardBackCache();
        return true;
    }
}
