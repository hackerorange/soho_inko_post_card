package com.soho.inko.service.impl;

import com.soho.inko.database.entity.OrderEntity;
import com.soho.inko.database.repository.OrderRepository;
import com.soho.inko.service.IOrderCacheService;
import com.soho.inko.utils.TypeChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ZhongChongtao on 2017/5/6.
 */
@Service
public class OrderCacheServiceImpl implements IOrderCacheService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderCacheServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderEntity> getAllOrderEntity() {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "createDate");
        return orderRepository.findAll(new Sort(order));
    }

    @Override
    @Cacheable(cacheNames = "order", key = "#orderId")
    public OrderEntity getOrderEntity(String orderId) throws NoSuchOrderException {
        OrderEntity orderEntity = orderRepository.findOne(orderId);
        if (TypeChecker.isNull(orderEntity)) {
            throw new NoSuchOrderException(orderId);
        }
        return orderEntity;
    }

    @Override
    public OrderEntity insertOrUpdateOrderEntity(String orderId, OrderEntity orderEntity) {
        orderRepository.findOne(orderId);
        return null;
    }
}
