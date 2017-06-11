package com.soho.inko.service;

import com.soho.inko.database.entity.OrderEntity;
import com.soho.inko.service.impl.NoSuchOrderException;

import java.util.List;

/**
 * Created by ZhongChongtao on 2017/5/6.
 */

public interface IOrderCacheService {
    /**
     * 获取所有订单
     *
     * @return 所有订单集合
     */
    public List<OrderEntity> getAllOrderEntity();

    /**
     * 根据订单ID，获取指定的订单信息
     *
     * @param orderId 订单ID
     * @return 订单信息
     * @throws NoSuchOrderException 当没有找到指定订单的时候，抛出此异常
     */
    public OrderEntity getOrderEntity(String orderId) throws NoSuchOrderException;

    /**
     * 插入或更新订单信息
     *
     * @param orderId     订单ID
     * @param orderEntity 订单实体
     * @return 插入或更新后的订单实体
     */
    public OrderEntity insertOrUpdateOrderEntity(String orderId, OrderEntity orderEntity);

}
