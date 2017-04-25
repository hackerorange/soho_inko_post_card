package com.soho.inko.service.impl;

import com.soho.inko.database.entity.EnvelopeEntity;
import com.soho.inko.database.repository.EnvelopeRepository;
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

    @Autowired
    public EnvelopeServiceImpl(EnvelopeRepository envelopeRepository, IPostCardService postCardService) {
        this.envelopeRepository = envelopeRepository;
        this.postCardService = postCardService;
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
}
