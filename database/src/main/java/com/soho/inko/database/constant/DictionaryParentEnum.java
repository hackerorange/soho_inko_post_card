package com.soho.inko.database.constant;

/**
 * Created by ZhongChongtao on 2017/4/27.
 * 字典表中父键对应的字段
 */
public enum DictionaryParentEnum {
    /**
     * 明信片反面
     */
    NEGATIVE_SIDE("明信片反面"),
    /**
     * 明信片尺寸
     */
    POST_CARD_SIZE("明信片尺寸"),
    /**
     * 明信片板式
     */
    POST_CARD_FORMAT("明信片板式");

    private String description;

    DictionaryParentEnum(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
