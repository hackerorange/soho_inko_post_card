package com.soho.inko.configuration.shiro.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.LinkedHashMap;

/**
 * Created by ZhongChongtao on 2017/3/18.
 */
@Configuration
@PropertySource("classpath:shiro.properties")
@ConfigurationProperties(prefix = "soho.shiro.filter")
public class ShiroFilterProperties {

    private LinkedHashMap<String, String> filterChainDefinition;

    public LinkedHashMap<String, String> getFilterChainDefinition() {
        return filterChainDefinition;
    }

    public void setFilterChainDefinition(LinkedHashMap<String, String> filterChainDefinition) {
        this.filterChainDefinition = filterChainDefinition;
    }
}
