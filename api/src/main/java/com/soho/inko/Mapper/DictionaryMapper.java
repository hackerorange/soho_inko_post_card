package com.soho.inko.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ZhongChongtao on 2017/4/28.
 */
@Mapper
public interface DictionaryMapper {
    public boolean deleteDictionaryByDictName(@Param("dictName") String dictName);

}
