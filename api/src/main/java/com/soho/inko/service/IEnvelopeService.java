package com.soho.inko.service;

import com.soho.inko.database.constant.CropTypeEnum;
import com.soho.inko.database.entity.EnvelopeEntity;
import com.soho.inko.image.Size;

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

    /**
     * 插入新的envelope信息
     *
     * @param orderId         订单ID
     * @param productSize     成品尺寸
     * @param formatType      版式ID
     * @param backgroundId    反面ID
     * @param cropType        裁切类型
     * @param productFileName 成品文件名称
     * @param copy            成品份数
     * @return 创建的envelope信息
     */
    public EnvelopeEntity insertNewEnvelope(String orderId, Size productSize, String formatType, String backgroundId, CropTypeEnum cropType, String productFileName, Integer copy);

    /**
     * 删除信封，包括信封下的所有的明信片
     *
     * @param envelopeId 信封ID
     * @return 是否删除成功
     */
    public boolean removeEnvelope(String envelopeId);

    /**
     * 查找envelope实体信息
     *
     * @param envelopeId 信封ID
     * @return 信封实体
     */
    public EnvelopeEntity findEnvelope(String envelopeId);
}
