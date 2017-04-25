package com.soho.inko.postcard.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ZhongChongtao on 2017/4/16.
 */
@Mapper
public interface PostCardMapper {

    public boolean deleteAllByPostCardNumber(@Param("orderNumber") String orderNumber);

}
