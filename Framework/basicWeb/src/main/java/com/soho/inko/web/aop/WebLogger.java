package com.soho.inko.web.aop;

import com.alibaba.fastjson.JSONObject;
import com.soho.inko.utils.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by ZhongChongtao on 2017/2/11.
 */
@Aspect
@Component
public class WebLogger {
    private Logger logger = Logger.getLogger(this.getClass());

    @AfterReturning(value = "execution(* *..controller..*.*(..))", returning = "response"
    )
    public void doAfter(Object response) {
        logger.info(StringUtils.repeat("-", 100));
        logger.info(String.format("[ %-20S ] : %s", "response", JSONObject.toJSONString(response)));
    }
}

