package com.soho.inko.context;

import com.soho.inko.database.entity.OrderEntity;

import java.util.List;

/**
 * Created by ZhongChongtao on 2017/4/28.
 */
public class OrderContext {
    private OrderEntity orderEntity;
    private List<EnvelopeContext> envelopeEntities;

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public List<EnvelopeContext> getEnvelopeEntities() {
        return envelopeEntities;
    }

    public void setEnvelopeEntities(List<EnvelopeContext> envelopeEntities) {
        this.envelopeEntities = envelopeEntities;
    }
}
