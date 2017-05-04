package com.soho.configuration.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * 根据bean名称，获取bean
     *
     * @param beanName bean名称
     * @return bean对象
     */
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    /**
     * 根据class获取bean
     *
     * @param clazz class
     * @return bean
     */
    public static <T> T getBean(Class<T> clazz) {
        try {
            return applicationContext.getBean(clazz);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据bean名称和bean的class，获取bean
     *
     * @param beanName bean名称
     * @param tClass   bean的className
     * @return bean对象
     */
    public static <T> T getBean(String beanName, Class<T> tClass) {
        return applicationContext.getBean(beanName, tClass);
    }

    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }
}