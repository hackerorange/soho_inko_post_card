package com.soho.inko.controller;

import com.soho.inko.database.entity.UserEntity;
import com.soho.inko.security.service.UserCacheService;
import com.soho.inko.utils.TypeChecker;
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
public class UserLoginController {
    private final UserCacheService userCacheService;

    @Autowired
    public UserLoginController(UserCacheService userCacheService) {
        this.userCacheService = userCacheService;
    }

    @GetMapping("login")
    public String getLogin() {
        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (TypeChecker.isNull(principal)) {
            return "user/login";
        }
        return "redirect:";
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
            return "redirect:";
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
        UserEntity userEntity = userCacheService.regist(userName, password, taoBaoId, email);
        if (TypeChecker.isNull(userEntity)) {
            model.addAttribute("error", "注册失败");
            return "user/regist";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            SecurityUtils.getSubject().login(token);
            return "redirect:";
        } catch (AuthenticationException e) {
            model.addAttribute("error", "注册失败");
            return "user/regist";
        }

    }

//    @GetMapping({"index", ""})
//    public String getIndex() {
//        return "index";
//    }

    @GetMapping("account/login")
    public String getIndex2() {
        return "user/login";
    }
}
