package com.soho.inko.controller;

import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by ZhongChongtao on 2017/5/8.
 */
@ControllerAdvice
public class WebExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnauthenticatedException.class)
    public String hadnlerUnauthenticatedException(UnauthenticatedException e, Model model) {
        logger.info("没有权限");
        return "error/405";
    }

}
