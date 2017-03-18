package com.soho.inko.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
/**
 * Created by ZhongChongtao on 2017/3/11.
 */
@Configuration
@ConfigurationProperties(prefix = "soho.file")
public class FileProperties {
    private String filePathTemplate = "D:/files/#YEAR/#MONTH/#DAY/#FILE_NAME";
    public String getFilePathTemplate() {
        return filePathTemplate;
    }
    public void setFilePathTemplate(String filePathTemplate) {
        this.filePathTemplate = filePathTemplate;
    }
}

