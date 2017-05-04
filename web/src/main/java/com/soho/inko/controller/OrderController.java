package com.soho.inko.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ZhongChongtao on 2017/4/24.
 */
@Controller
@RequestMapping("background/order")
public class OrderController {
    @GetMapping("")
    public String getOrderAll() {
        return "background/order/order_view";
    }

    @GetMapping("{orderId}")
    public ModelAndView getOrderDetail(@PathVariable("orderId") String orderId, ModelAndView view) {
        view.getModel().put("orderId", orderId);
        view.setViewName("background/order/order_detail");
        return view;
    }
}
