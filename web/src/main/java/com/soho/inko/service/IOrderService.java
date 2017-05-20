package com.soho.inko.service;

import com.soho.inko.database.entity.OrderEntity;

import java.util.List;

/**
 * Created by ZhongChongtao on 2017/5/6.
 */

public interface IOrderService {
    /**
     * 获取所有订单
     *
     * @return 所有订单集合
     */
    public List<OrderEntity> getAllOrderEntity();

}
