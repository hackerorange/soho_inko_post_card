package com.soho.inko.service;


import com.soho.inko.database.entity.SizeEntity;

import java.util.List;

/**
 * Created by ZhongChongtao on 2017/3/29.
 */
public interface ISizeService {
    /**
     * 插入新的尺寸
     *
     * @param name   尺寸名称
     * @param width  尺寸宽度
     * @param height 尺寸高度
     * @return 插入后的尺寸实体
     */
    public SizeEntity insertSize(String name, int width, int height);

    /**
     * 根据ID删除指定尺寸信息
     *
     * @param id 尺寸ID
     */
    public void deleteSize(String id);

    /**
     * 获取所有尺寸（按照宽度、高度由小到大排序）
     *
     * @return 所有尺寸
     */
    public List<SizeEntity> getAllSize();

}
