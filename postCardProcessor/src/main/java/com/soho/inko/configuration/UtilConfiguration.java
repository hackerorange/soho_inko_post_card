package com.soho.inko.configuration;

import com.soho.inko.configuration.properties.UtilsProperties;
import com.soho.inko.utils.PictureUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 工具类初始化参数注入
 * Created by ZhongChongtao on 2017/3/26.
 */
@Configuration
public class UtilConfiguration {

    @Bean
    public PictureUtils cutPic(UtilsProperties properties) {
        PictureUtils.setPictureMagicPath(properties.getImageMagicPath());
        return null;
    }
}
