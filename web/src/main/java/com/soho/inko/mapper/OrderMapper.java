package com.soho.inko.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ZhongChongtao on 2017/5/7.
 */
@Mapper
public interface OrderMapper {

    public boolean updateOrderStatus(@Param("orderId") String orderId, @Param("orderStatusEnum") String orderStatusEnum, @Param("updateAccountId") String updateAccountId);

}
