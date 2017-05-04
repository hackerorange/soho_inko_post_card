package com.soho.inko.configuration.shiro.configuration;

import com.soho.inko.configuration.shiro.configuration.interceptor.ApiBasicPathInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by ZhongChongtao on 2017/2/12.
 */
@Configuration
public class ClientWebConfiguration extends WebMvcConfigurerAdapter {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 日志开始
        registry.addInterceptor(new ApiBasicPathInterceptor()).addPathPatterns("/**");
    }

}

