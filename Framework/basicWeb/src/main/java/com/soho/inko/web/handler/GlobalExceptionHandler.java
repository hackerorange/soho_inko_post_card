package com.soho.inko.web.handler;

import com.alibaba.fastjson.JSONObject;
import com.soho.inko.utils.StringUtils;
import com.soho.inko.web.response.AbstractResponse;
import com.soho.inko.web.response.BodyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;


/**
 * 全局错误异常
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public AbstractResponse handlerMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error(String.format("[ %20s ] : %s", "missing param", e.getParameterName()));
        BodyResponse bodyResponse = new BodyResponse();
        bodyResponse.setMessage("缺少必要的参数" + e.getParameterName());
        logger.info(StringUtils.repeat("-", 100));
        logger.info(String.format("[ %-20S ] : %s", "response", JSONObject.toJSONString(bodyResponse)));
        return bodyResponse;
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public AbstractResponse handlerIllegalArgumentException(IllegalArgumentException e) {
        logger.error(String.format("[ %20s ] : %s", "illegalArgumentException", e.getMessage()));
        BodyResponse bodyResponse = new BodyResponse();
        bodyResponse.setMessage("something happened");
        bodyResponse.setCode(80);
        logger.info(StringUtils.repeat("-", 100));
        bodyResponse.setKey("E00001");
        logger.info(String.format("[ %-20S ] : %s", "response", JSONObject.toJSONString(bodyResponse)));
        return bodyResponse;
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public AbstractResponse handlerConstraintViolationException(ConstraintViolationException e){
        logger.error(String.format("[ %20s ] : %s", "ConstraintViolationException", e.getLocalizedMessage()));
        BodyResponse bodyResponse = new BodyResponse();
        bodyResponse.setMessage(e.getLocalizedMessage());
        bodyResponse.setCode(80);
        logger.info(StringUtils.repeat("-", 100));
        bodyResponse.setKey("E00001");
        logger.info(String.format("[ %-20S ] : %s", "response", JSONObject.toJSONString(bodyResponse)));
        return bodyResponse;
    }

}

