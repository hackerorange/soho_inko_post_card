package com.soho.inko.configuration.shiro.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * Created by ZhongChongtao on 2017/3/30.
 */

@Configuration
@ConfigurationProperties(prefix = "soho.web")
public class WebProperties {

    private Map<String, String> viewControllers;
    private String apiBasePath = "http://localhost:8100/api";
    private String fileBasePath = "http://localhost:8089/file";

    public String getFileBasePath() {
        return fileBasePath;
    }

    public void setFileBasePath(String fileBasePath) {
        this.fileBasePath = fileBasePath;
    }

    public String getApiBasePath() {
        return apiBasePath;
    }

    public void setApiBasePath(String apiBasePath) {
        this.apiBasePath = apiBasePath;
    }

    public Map<String, String> getViewControllers() {
        return viewControllers;
    }

    public void setViewControllers(Map<String, String> viewControllers) {
        this.viewControllers = viewControllers;
    }
}
