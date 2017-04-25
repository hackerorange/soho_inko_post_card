package com.soho.inko.service.impl;

import com.soho.inko.database.entity.FormatTypeEntity;
import com.soho.inko.database.repository.FormatTypeRepository;
import com.soho.inko.service.IFormatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZhongChongtao on 2017/4/3.
 */
@Service
public class FormatTypeServiceImpl implements IFormatTypeService {
    private final FormatTypeRepository cardTypeRepository;

    @Autowired
    public FormatTypeServiceImpl(FormatTypeRepository cardTypeRepository) {
        this.cardTypeRepository = cardTypeRepository;
    }

    @Override
    public FormatTypeEntity getCardType(String id) {
        return cardTypeRepository.findOne(id);
    }
}
