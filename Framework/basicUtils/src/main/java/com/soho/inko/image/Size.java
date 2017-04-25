package com.soho.inko.image;

/**
 * Created by ZhongChongtao on 2017/3/25.
 */
public class Size implements Cloneable {
    private double width;
    private double height;

    public Size() {
    }

    public Size(double width, double height) {

        this.width = width;
        this.height = height;
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
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
