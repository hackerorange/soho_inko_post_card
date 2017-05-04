package com.soho.inko.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ZhongChongtao on 2017/4/16.
 */
@Mapper
public interface PostCardMapper {

    public boolean deleteAllByOrderId(@Param("orderId") String orderId);

    public boolean deleteAllByEnvelopeId(String envelopeId);

}
