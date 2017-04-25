package com.soho.inko.domain;

/**
 * Created by ZhongChongtao on 2017/3/23.
 */
public class CropInfo {
    /**
     * 旋转角度
     */
    private int rotation;
    /**
     * 左侧裁切
     */
    private double left;
    /**
     * 上侧裁切
     */
    private double top;
    /**
     * 裁切宽度
     */
    private double width;
    /**
     * 裁切高度
     */
    private double height;

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public double getLeft() {
        return left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getTop() {
        return top;
    }

    public void setTop(double top) {
        this.top = top;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "CropInfo{" +
                "left=" + left +
                ", top=" + top +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
