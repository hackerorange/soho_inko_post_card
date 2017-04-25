package com.soho.inko.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ZhongChongtao on 2017/3/25.
 */

@Configuration
@ConfigurationProperties
public class FileServerProperties {
    private String uploadUrl = "http://localhost:8089/file/upload";
    private String tmpPath = "D:/path/";
    private String downloadUrl = "http://localhost:8089/file/{fileId}";

    public String getTmpPath() {
        return tmpPath;
    }

    public void setTmpPath(String tmpPath) {
        this.tmpPath = tmpPath;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
