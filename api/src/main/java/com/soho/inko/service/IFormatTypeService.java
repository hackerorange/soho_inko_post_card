package com.soho.inko.service;


import com.soho.inko.database.entity.FormatTypeEntity;

/**
 * Created by ZhongChongtao on 2017/4/3.
 */
public interface IFormatTypeService {
    /**
     * 根据明信片板式类型ID获取明信片
     *
     * @param id 明信片板式ID
     * @return 明信片板式
     */
    public FormatTypeEntity getCardType(String id);
}
