package com.soho.inko.constant;

/**
 * Created by ZhongChongtao on 2017/3/28.
 */
public enum UnitTypeEnum {

    MILLIMETRE("毫米", 1),
    CENTIMETRE("厘米", 10),
    INCH("英寸", 25.39185),
    PIX("像素", 2.8355555030452684621246581088026);

    private String name;
    private double baseRatio;

    UnitTypeEnum(String name, double baseRatio) {
        this.name = name;
        this.baseRatio = baseRatio;
    }

    public static void main(String[] args) {
        System.out.println(UnitTypeEnum.MILLIMETRE.convertTo(1, UnitTypeEnum.PIX));
    }

    public String getName() {
        return name;
    }

    public double getBaseRatio() {
        return baseRatio;
    }

    /**
     * 转化单位
     *
     * @param value      要转化的数值
     * @param targetType 转化后的单位
     * @return 转化后的数值
     */
    public double convertTo(double value, UnitTypeEnum targetType) {
        return value * targetType.getBaseRatio() / this.getBaseRatio();
    }
}
