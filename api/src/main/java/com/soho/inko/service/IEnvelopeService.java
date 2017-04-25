package com.soho.inko.service;

import com.soho.inko.database.entity.EnvelopeEntity;

import java.util.List;

/**
 * Created by ZhongChongtao on 2017/4/16.
 */
public interface IEnvelopeService {
    /**
     * 根据OrderId获取所有的Envelope
     *
     * @param orderId orderId
     * @return 所有的envelopeEntity
     */
    public List<EnvelopeEntity> findAllByOrderId(String orderId);

    /**
     * 根据orderId，删除所有的envelope
     *
     * @param orderId orderId
     * @return 是否删除成功
     */
    public boolean removeAllByOrderId(String orderId);
}
