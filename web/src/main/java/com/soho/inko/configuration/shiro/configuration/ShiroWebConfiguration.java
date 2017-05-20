package com.soho.inko.configuration.shiro.configuration;

import com.soho.inko.configuration.shiro.configuration.properties.WebProperties;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Map;

/**
 * Created by ZhongChongtao on 2017/3/30.
 */
@Configuration
public class ShiroWebConfiguration extends WebMvcConfigurerAdapter {
    private final WebProperties webProperties;

    @Autowired
    public ShiroWebConfiguration(WebProperties webProperties) {
        this.webProperties = webProperties;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        for (Map.Entry<String, String> stringStringEntry : webProperties.getViewControllers().entrySet()) {
            registry.addViewController(stringStringEntry.getKey())
                    .setViewName(stringStringEntry.getValue());
        }
        super.addViewControllers(registry);
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean(SecurityManager securityManager) {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(new Object[]{securityManager});
        return methodInvokingFactoryBean;
    }
}
