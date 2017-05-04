package com.soho.inko.database.repository;

import com.soho.inko.database.entity.DictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ZhongChongtao on 2017/4/27.
 */
public interface DictionaryRepository extends JpaRepository<DictionaryEntity, String> {
    /**
     * 根据父字典ID,查找所有字典
     *
     * @param dictionaryParentEnum 父字典ID
     * @return 父字典下的所有字典实体
     */
    public List<DictionaryEntity> findAllByParentId(String dictionaryParentEnum);

    /**
     * 根据字典名称，查找字典
     *
     * @param dictionaryName 字典名称
     * @return 字典实体
     */
    public DictionaryEntity findByDicName(String dictionaryName);

    

}
