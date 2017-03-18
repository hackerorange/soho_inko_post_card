package com.soho.framework.web.interceptor;

import com.soho.framework.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;


/**
 * Created by ZhongChongtao on 2017/2/27.
 */
@Component
public class WebLogInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        logger.info(StringUtils.repeat("=", 100));
        startTime.set(System.currentTimeMillis());
        logger.info(String.format("[ %-20s ] : %s", "URL", request.getRequestURL().toString()));
        logger.info(String.format("[ %-20s ] : %s", "HTTP_METHOD", request.getMethod()));
        logger.info(String.format("[ %-20s ] : %s", "IP", request.getRemoteAddr()));
        logger.info(String.format("[ %-20s ] : %s", "CLASS_METHOD", handlerMethod.getBeanType().getName() + "." + handlerMethod.getMethod().getName()));
//      logger.info(String.format("[ %-20s ] : %s", "ARGS", Arrays.toString(joinPoint.getArgs())));
        Enumeration<String> enu = request.getParameterNames();
        if (enu.hasMoreElements()) {
            logger.info(String.format("[ %-20s ] : ", "REQUEST_PARAMS"));
        }
        while (enu.hasMoreElements()) {
            String paraName = enu.nextElement();
            logger.info(String.format("  %-20s   : %s", paraName, request.getParameter(paraName)));
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info(String.format("[ %-20S ] : %s", "Time-consuming", (System.currentTimeMillis() - startTime.get())));
        logger.info(StringUtils.repeat("=", 100));
        logger.info("  ");
    }
}

