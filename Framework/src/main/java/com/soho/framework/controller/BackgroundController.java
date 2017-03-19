package com.soho.framework.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ZhongChongtao on 2017/3/18.
 */
@Controller
@RequiresPermissions("admin")
@RequestMapping("background")
public class BackgroundController {
    @RequiresPermissions("admin")
    @RequestMapping({"", "/"})
    public String background() {
        return "background/index";
    }
}
