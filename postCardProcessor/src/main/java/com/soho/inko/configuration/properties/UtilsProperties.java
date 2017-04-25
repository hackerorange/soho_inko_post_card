package com.soho.inko.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ZhongChongtao on 2017/3/26.
 */
@Configuration
@ConfigurationProperties(prefix = "soho.utils")
public class UtilsProperties {
    private String imageMagicPath = "D:/Program Files (x86)/GraphicsMagick-1.3.25-Q16";

    public String getImageMagicPath() {
        return imageMagicPath;
    }

    public void setImageMagicPath(String imageMagicPath) {
        this.imageMagicPath = imageMagicPath;
    }
}
