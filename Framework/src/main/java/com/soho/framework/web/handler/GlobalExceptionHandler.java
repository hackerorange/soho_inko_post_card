package com.soho.framework.web.handler;

import com.alibaba.fastjson.JSONObject;
import com.soho.framework.common.utils.StringUtils;
import com.soho.framework.web.response.AbstractResponse;
import com.soho.framework.web.response.BodyResponse;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.constraints.Null;


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
        BodyResponse<Null> bodyResponse = new BodyResponse<>();
        bodyResponse.setMessage("缺少必要的参数" + e.getParameterName());
        logger.info(StringUtils.repeat("-", 100));
        logger.info(String.format("[ %-20S ] : %s", "response", JSONObject.toJSONString(bodyResponse)));
        return bodyResponse;
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public AbstractResponse handlerIllegalArgumentException(IllegalArgumentException e) {
        logger.error(String.format("[ %20s ] : %s", "illegalArgumentException", e.getMessage()));
        BodyResponse<Null> bodyResponse = new BodyResponse<>();
        bodyResponse.setMessage("something happened");
        bodyResponse.setCode(80);
        logger.info(StringUtils.repeat("-", 100));
        bodyResponse.setKey("E00001");
        logger.info(String.format("[ %-20S ] : %s", "response", JSONObject.toJSONString(bodyResponse)));
        return bodyResponse;
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(AuthorizationException.class)
    public String handlerAuthorizationException(AuthorizationException exception, Model model) {
        model.addAttribute("error", "该用户所在的用户组没有此权限");
        model.addAttribute("status", "405");
        model.addAttribute("message", exception.getLocalizedMessage());
        return "error/error";
    }

//    @ExceptionHandler(Exception.class)
//    public AbstractResponse handlerAllException(Exception e) {
//        logger.error(e.getMessage(), e);
//        BodyResponse<Null> bodyResponse = new BodyResponse<>();
//        bodyResponse.setMessage("系统正在维护中");
//        bodyResponse.setCode(80);
//        bodyResponse.setKey("E00000");
//        logger.info(StringUtils.repeat("-", 100));
//        logger.info(String.format("[ %-20S ] : %s", "response", JSONObject.toJSONString(bodyResponse)));
//        return bodyResponse;
//    }
}

