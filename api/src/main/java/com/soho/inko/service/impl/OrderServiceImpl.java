package com.soho.inko.service.impl;

import com.soho.inko.database.constant.OrderStatusEnum;
import com.soho.inko.database.entity.OrderEntity;
import com.soho.inko.database.repository.OrderRepository;
import com.soho.inko.database.repository.UserRepository;
import com.soho.inko.service.IEnvelopeService;
import com.soho.inko.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by ZhongChongtao on 2017/4/16.
 */
@Service
public class OrderServiceImpl implements IOrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final IEnvelopeService envelopeService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrderServiceImpl(UserRepository userRepository, OrderRepository orderRepository, IEnvelopeService envelopeService) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.envelopeService = envelopeService;
    }

    @Override
    public OrderEntity createOrder(String taobaoId, String tokenId) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomerTaobaoId(taobaoId);
        orderEntity.setCreateAccountId(tokenId);
        orderRepository.save(orderEntity);
        return orderEntity;
    }

    @Override
    public List<OrderEntity> findAllByStatus(OrderStatusEnum orderStatusEnum) {
        return orderRepository.findAllByOrderStatus(orderStatusEnum);
    }

    @Override
    public List<OrderEntity> findAllByDate(Date date) {
        return null;
    }

    @Override
    public boolean deleteByOrderId(String orderId) {
        //TODO:彻底删除所有的任务
        //删除Envelope
        envelopeService.removeAllByOrderId(orderId);
        orderRepository.delete(orderId);
        return true;
    }

    @Override
    public OrderEntity findOrderById(String orderId) {
        return orderRepository.findOne(orderId);
    }
}
