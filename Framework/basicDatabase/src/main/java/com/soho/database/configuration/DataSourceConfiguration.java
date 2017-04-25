package com.soho.database.configuration;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.soho.database.configuration.properties.DataSourceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by ZhongChongtao on 2017/3/18.
 */
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceConfiguration {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    @Primary
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        DataSource dataSource = null;
        try {
            dataSource = DruidDataSourceFactory.createDataSource(dataSourceProperties.getConnectionProperties().get("postcardtailor"));
            logger.info("connect database success");
        } catch (Exception e) {
            logger.info("connect database failure");
        }
        return dataSource;
    }
}
