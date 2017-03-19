//package com.soho.framework.controller;
//
//import com.alibaba.fastjson.JSONObject;
//import com.soho.framework.common.utils.StringUtils;
//import com.soho.framework.web.response.AbstractResponse;
//import com.soho.framework.web.response.BodyResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.autoconfigure.web.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.ServletRequest;
//
//
//@Controller
//public class MainsiteErrorController implements ErrorController {
//
//    private static final String ERROR_PATH = "/error";
//
//    @ResponseBody
//    @RequestMapping(value = ERROR_PATH)
//    public AbstractResponse handleError(ServletRequest request,Exception e) {
//        BodyResponse bodyResponse = new BodyResponse<>();
//        bodyResponse.setMessage("系统正在维护中");
//        bodyResponse.setCode(80);
//        bodyResponse.setKey("E00000");
//        return bodyResponse;
//    }
//
//    @Override
//    public String getErrorPath() {
//        // TODO Auto-generated method stub
//        return ERROR_PATH;
//    }
//
//}