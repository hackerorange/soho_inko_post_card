package com.soho.inko.service.impl;

import com.soho.inko.database.entity.SizeEntity;
import com.soho.inko.database.repository.SizeRepository;
import com.soho.inko.service.ISizeService;
import com.soho.inko.utils.TypeChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ZhongChongtao on 2017/3/29.
 */
@Service
public class SizeServiceImpl implements ISizeService {
    private final SizeRepository sizeRepository;

    @Autowired
    public SizeServiceImpl(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    @Override
    public SizeEntity insertSize(String name, int width, int height) {
        //根据名称查找尺寸
        SizeEntity byName = sizeRepository.findByName(name);
        //尺寸不存在，创建新的尺寸并且持久化
        if (TypeChecker.isNull(byName)) {
            SizeEntity sizeEntity = new SizeEntity();
            sizeEntity.setWidth(width);
            sizeEntity.setHeight(height);
            sizeEntity.setName(name);
            byName = sizeRepository.save(sizeEntity);
        }
        return byName;
    }

    @Override
    public void deleteSize(String id) {
        sizeRepository.delete(id);
    }

    @Override
    public List<SizeEntity> getAllSize() {
        Sort sort = new Sort(Sort.Direction.ASC, "width", "height");
        return sizeRepository.findAll(sort);
    }
}
