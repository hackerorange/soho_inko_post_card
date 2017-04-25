package com.soho.inko.database.repository;

import com.soho.inko.database.constant.OrderStatusEnum;
import com.soho.inko.database.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by ZhongChongtao on 2017/4/16.
 */
public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    public List<OrderEntity> findAllByOrderStatus(OrderStatusEnum orderStatus);
}
