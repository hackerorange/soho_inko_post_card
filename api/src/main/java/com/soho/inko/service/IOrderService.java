package com.soho.inko.service;

import com.soho.inko.database.constant.OrderStatusEnum;
import com.soho.inko.database.entity.OrderEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by ZhongChongtao on 2017/4/16.
 */
public interface IOrderService {
    /**
     * 根据淘宝ID生成新的订单
     *
     * @param taobaoId 用户淘宝ID
     * @param tokenId
     * @return 生成的新订单信息
     */
    OrderEntity createOrder(String taobaoId, String tokenId);

    /**
     * 根据订单状态，获取所有订单
     *
     * @param orderStatusEnum 订单状态
     * @return 所有订单
     */
    List<OrderEntity> findAllByStatus(OrderStatusEnum orderStatusEnum);

    /**
     * 根据订单状态，获取所有订单
     *
     * @param date 日期
     * @return 所有订单
     */
    List<OrderEntity> findAllByDate(Date date);

    /**
     * 根据订单ID,删除指定的订单
     *
     * @param orderId 订单ID
     * @return 订单
     */
    boolean deleteByOrderId(String orderId);

    /**
     * 根据订单ID，获取订单
     *
     * @param orderId 订单ID
     * @return 对应的订单
     */
    OrderEntity findOrderById(String orderId);
}
