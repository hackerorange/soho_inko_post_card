package com.soho.inko.service.impl;

import com.soho.inko.database.constant.OrderStatusEnum;
import com.soho.inko.database.entity.OrderEntity;
import com.soho.inko.database.entity.UserEntity;
import com.soho.inko.database.repository.OrderRepository;
import com.soho.inko.database.repository.UserRepository;
import com.soho.inko.service.IEnvelopeService;
import com.soho.inko.service.IOrderService;
import com.soho.inko.utils.TypeChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        //查询用户信息
        UserEntity userEntity = userRepository.findByTaoBaoId(taobaoId);
        //如果没有查询到用户信息
        if (TypeChecker.isNull(userEntity)) {
            userEntity = new UserEntity();
            userEntity.setTaoBaoId(taobaoId);
            userEntity.setUserName(taobaoId);
            userEntity.setSalt(UUID.randomUUID().toString());
            userEntity = userRepository.save(userEntity);
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomerAccountId(userEntity.getId());
        orderEntity.setCustomerTaobaoId(userEntity.getTaoBaoId());
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
}
