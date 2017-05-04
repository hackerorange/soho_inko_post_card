package com.soho.inko.configuration.shiro.configuration.interceptor;

import com.soho.configuration.context.ApplicationContextHolder;
import com.soho.inko.configuration.shiro.configuration.properties.WebProperties;
import com.soho.inko.utils.TypeChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ZhongChongtao on 2017/4/29.
 */
public class ApiBasicPathInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        WebProperties webProperties = ApplicationContextHolder.getBean(WebProperties.class);
        if (TypeChecker.isNull(webProperties)) {
            logger.info("获取webProperties失败");
            return;
        }
        request.setAttribute("apiBasePath", webProperties.getApiBasePath());
        request.setAttribute("fileBasePath", webProperties.getFileBasePath());
        logger.info("apiBasePath为" + webProperties.getApiBasePath());
        super.postHandle(request, response, handler, modelAndView);
    }
}
