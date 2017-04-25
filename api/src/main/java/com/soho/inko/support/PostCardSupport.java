package com.soho.inko.support;

import com.soho.inko.database.entity.FormatTypeEntity;
import com.soho.inko.image.Point;
import com.soho.inko.image.Size;

/**
 * Created by ZhongChongtao on 2017/4/3.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class PostCardSupport {

    /**
     * 根据成品尺寸和板式，获取图像区域尺寸
     *
     * @param product          成品尺寸
     * @param formatTypeEntity 板式类型
     * @return 图像区域尺寸
     */
    public static Size getPictureAreaSizeByPostCardType(Size product, FormatTypeEntity formatTypeEntity) {
        Size size = new Size();
        size.setWidth(product.getWidth());
        //按照padding初始化尺寸
        size.setHeight(product.getHeight() - formatTypeEntity.getPaddingTop() - formatTypeEntity.getPaddingBottom());
        size.setWidth(product.getWidth() - formatTypeEntity.getPaddingLeft() - formatTypeEntity.getPaddingRight());
        if (formatTypeEntity.getRatable()) {
            if (size.getWidth() / size.getHeight() > formatTypeEntity.getRatio()) {
                size.setWidth(size.getHeight() * formatTypeEntity.getRatio());
            } else {
                size.setHeight(size.getWidth() / formatTypeEntity.getRatio());
            }
        }
        return size;
    }

    /**
     * 根据成品尺寸和版式类型，获取图像区域在成品中的位置
     *
     * @param product          成品尺寸
     * @param formatTypeEntity 板式信息
     * @return 图像区域在成品的位置
     */
    public static Point getPictureAreaLocationByPostCardType(Size product, FormatTypeEntity formatTypeEntity) {
        return new Point(formatTypeEntity.getPaddingLeft(), formatTypeEntity.getPaddingTop());
    }
}
