package com.soho.inko.controller;

import com.soho.inko.database.constant.OrderStatusEnum;
import com.soho.inko.database.entity.UserEntity;
import com.soho.inko.mapper.OrderMapper;
import com.soho.inko.service.IOrderService;
import com.soho.inko.utils.TypeChecker;
import com.soho.inko.web.response.StandardResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ZhongChongtao on 2017/4/24.
 */
@Controller
@RequestMapping("order")
public class OrderController {

    private final IOrderService orderService;

    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(IOrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @ResponseBody
    @PutMapping("{orderId}/changeStatus")
    public StandardResponse changeOrderStatus(@RequestParam OrderStatusEnum orderStatusEnum,
                                              @PathVariable("orderId") String orderId) {
        System.out.println(SecurityUtils.getSubject().getPrincipal());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        UserEntity user = (UserEntity) request.getAttribute("user");
        StandardResponse standardResponse = new StandardResponse();
        if (!TypeChecker.isNull(user)) {
            if (orderMapper.updateOrderStatus(orderId, orderStatusEnum.name(), user.getId())) {
                standardResponse.success();
                return standardResponse;
            }
        }
        standardResponse.failure();
        return standardResponse;
    }

    @GetMapping("")
    public String getOrderAll(Model model) {
        model.addAttribute("orders", orderService.getAllOrderEntity());
        return "background/order/order_view";
    }

    @RequiresPermissions("order:create")
    @GetMapping("new")
    public ModelAndView getCreateOrderPage(ModelAndView view) {
        view.setViewName("background/order/order_new");
        return view;
    }


    @GetMapping("{orderId}")
    public ModelAndView getOrderDetail(@PathVariable("orderId") String orderId, ModelAndView view) {
        view.getModel().put("orderId", orderId);
        view.setViewName("background/order/order_detail");
        return view;
    }


}
