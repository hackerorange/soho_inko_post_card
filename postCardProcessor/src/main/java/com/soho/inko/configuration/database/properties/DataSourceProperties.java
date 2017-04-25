package com.soho.inko.configuration.database.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/**
 * Created by ZhongChongtao on 2017/3/18.
 */
@Configuration
@PropertySource(value = "classpath:datasource.properties")
@ConfigurationProperties(prefix = "soho.database")
public class DataSourceProperties {
    private Map<String, Map<String, String>> connectionProperties;

    public Map<String, Map<String, String>> getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(Map<String, Map<String, String>> connectionProperties) {
        this.connectionProperties = connectionProperties;
    }
}
