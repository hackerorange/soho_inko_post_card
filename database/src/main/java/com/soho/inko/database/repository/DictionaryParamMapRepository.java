package com.soho.inko.database.repository;

import com.soho.inko.database.entity.DictionaryParamMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ZhongChongtao on 2017/4/27.
 */
public interface DictionaryParamMapRepository extends JpaRepository<DictionaryParamMapEntity, Integer> {
    /**
     * 根据字典ID，获取所有参数
     *
     * @param dictionaryId 字典ID
     * @return 所有参数
     */
    public List<DictionaryParamMapEntity> findAllByDictionaryId(String dictionaryId);

}
