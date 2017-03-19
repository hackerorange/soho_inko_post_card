package com.soho.framework.controller;

import com.soho.framework.common.utils.TypeChecker;
import com.soho.framework.security.entity.UserEntity;
import com.soho.framework.security.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ZhongChongtao on 2017/3/18.
 */
@Controller
public class TestController {
    private final UserService userService;

    @Autowired
    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String getLogin() {
        return "user/login";
    }

    /**
     * 登陆失败调用此接口
     *
     * @param request HttpRequest请求，用于从中获取登陆错误信息
     * @param model   model，用于向页面发送数据信息
     * @return 登陆界面
     */
    @PostMapping(value = "login", params = {"userName", "password"})
    public String postLogin(HttpServletRequest request, Model model) {
        Object logError = request.getAttribute("loginError");
        if (StringUtils.isEmpty(logError)) {
            return "redirect:index";
        }

//        System.out.println(loginError);
        model.addAttribute("error", logError);
        return "user/login";
    }

    @GetMapping("regist")
    public String getRegist() {
        return "user/regist";
    }

    @PostMapping("regist")
    public String postRegist(String userName, String password, String taoBaoId, String email, Model model) {
        UserEntity userEntity = userService.regist(userName, password, taoBaoId, email);
        if (TypeChecker.isNull(userEntity)) {
            model.addAttribute("error", "注册失败");
            return "user/regist";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            SecurityUtils.getSubject().login(token);
            return "redirect:index";
        } catch (AuthenticationException e) {
            model.addAttribute("error", "注册失败");
            return "user/regist";
        }

    }

    @GetMapping({"index",""})
    public String getIndex() {
        return "index";
    }

    @GetMapping("account/login")
    public String getIndex2() {
        return "user/login";
    }
}
