package com.soho.inko.service.impl;

import com.soho.inko.Mapper.PostCardMapper;
import com.soho.inko.database.constant.CropTypeEnum;
import com.soho.inko.database.entity.EnvelopeEntity;
import com.soho.inko.database.repository.EnvelopeRepository;
import com.soho.inko.database.repository.PostCardRepository;
import com.soho.inko.image.Size;
import com.soho.inko.service.IEnvelopeService;
import com.soho.inko.service.IPostCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ZhongChongtao on 2017/4/16.
 */
@Service
public class EnvelopeServiceImpl implements IEnvelopeService {
    private final EnvelopeRepository envelopeRepository;
    private final IPostCardService postCardService;
    private final PostCardMapper postCardMapper;
    private final PostCardRepository postCardRepository;

    @Autowired
    public EnvelopeServiceImpl(EnvelopeRepository envelopeRepository, IPostCardService postCardService, PostCardRepository postCardRepository, PostCardMapper postCardMapper) {
        this.envelopeRepository = envelopeRepository;
        this.postCardService = postCardService;
        this.postCardRepository = postCardRepository;
        this.postCardMapper = postCardMapper;
    }

    @Override
    public List<EnvelopeEntity> findAllByOrderId(String orderId) {
        return envelopeRepository.findAllByOrderId(orderId);
    }

    @Override
    public boolean removeAllByOrderId(String orderId) {
        postCardService.deletePostCardInfoByOrderId(orderId);
        envelopeRepository.deleteByOrderId(orderId);
        return true;
    }

    @Override
    public EnvelopeEntity insertNewEnvelope(String orderId, Size productSize, String formatType, String backgroundId, CropTypeEnum cropType, String productFileName, Integer copy) {
        EnvelopeEntity envelopeEntity = new EnvelopeEntity();
        envelopeEntity.setProductWidth((int) productSize.getWidth());
        envelopeEntity.setProductHeight((int) productSize.getHeight());
        envelopeEntity.setFormatTypeId(formatType);
        envelopeEntity.setBackgroundId(backgroundId);
        envelopeEntity.setCropType(cropType);
        envelopeEntity.setOrderId(orderId);
        envelopeEntity.setProductCopy(copy);
        envelopeEntity.setProductFileName(productFileName);
        envelopeEntity = envelopeRepository.save(envelopeEntity);
        return envelopeEntity;
    }

    @Override
    public boolean removeEnvelope(String envelopeId) {
        postCardMapper.deleteAllByEnvelopeId(envelopeId);
        envelopeRepository.delete(envelopeId);
        return true;
    }

    @Override
    public EnvelopeEntity findEnvelope(String envelopeId) {
        return envelopeRepository.findOne(envelopeId);
    }
}
