package com.soho.framework.configuration.web;

import com.soho.framework.web.interceptor.WebLastInterceptor;
import com.soho.framework.web.interceptor.WebLogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

/**
 * Created by ZhongChongtao on 2017/2/12.
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 日志开始
        registry.addInterceptor(new WebLogInterceptor()).addPathPatterns("/**");
        // 日志结束
        registry.addInterceptor(new WebLastInterceptor()).addPathPatterns("/**");
    }
    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties exceptionMappings = new Properties();
        simpleMappingExceptionResolver.setExceptionMappings(exceptionMappings);
        exceptionMappings.put("org.apache.shiro.authz.AuthorizationException", "/error");
        return simpleMappingExceptionResolver;
    }

}

