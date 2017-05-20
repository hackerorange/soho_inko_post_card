package com.soho.inko.database.constant;

/**
 * Created by ZhongChongtao on 2017/4/16.
 */
public enum OrderStatusEnum {
    CREATE("创建"),
    START("开始制作"),
    PRINT("打印完成"),
    FINISH("制作完成"),;

    private String desc;

    OrderStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
