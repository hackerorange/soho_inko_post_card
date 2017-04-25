package com.soho.inko.domain;


import com.soho.inko.database.entity.FormatTypeEntity;
import com.soho.inko.image.Point;
import com.soho.inko.image.Size;

/**
 * Created by ZhongChongtao on 2017/3/23.
 */
public class PostCardInfoDTO {
    //明信片ID
    private String postCardId;
    //明信片文件ID
    private String postCardFileId;
    /**
     * 明信片板式相关信息
     */
    private FormatTypeEntity formatTypeEntity;
    /**
     * 成品尺寸
     */
    private Size productSize;
    /**
     * 图片区域尺寸
     */
    private Size pictureSize;
    /**
     * 图像区域在整个图片中的坐标
     */
    private Point location;
    //裁切结果
    private CropInfo cropInfo;

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public String getPostCardId() {
        return postCardId;
    }

    public void setPostCardId(String postCardId) {
        this.postCardId = postCardId;
    }

    public String getPostCardFileId() {
        return postCardFileId;
    }

    public void setPostCardFileId(String postCardFileId) {
        this.postCardFileId = postCardFileId;
    }

    public Size getProductSize() {
        return productSize;
    }

    public void setProductSize(Size productSize) {
        this.productSize = productSize;
    }

    public Size getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(Size pictureSize) {
        this.pictureSize = pictureSize;
    }

    public CropInfo getCropInfo() {
        return cropInfo;
    }

    public void setCropInfo(CropInfo cropInfo) {
        this.cropInfo = cropInfo;
    }
}
