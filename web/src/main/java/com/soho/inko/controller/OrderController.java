package com.soho.inko.controller;

import com.soho.inko.database.constant.OrderStatusEnum;
import com.soho.inko.database.entity.OrderEntity;
import com.soho.inko.database.entity.UserEntity;
import com.soho.inko.mapper.OrderMapper;
import com.soho.inko.service.IOrderCacheService;
import com.soho.inko.service.impl.NoSuchOrderException;
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

    private final IOrderCacheService orderService;

    private final OrderMapper orderMapper;

    @Autowired
    public OrderController(IOrderCacheService orderService, OrderMapper orderMapper) {
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

    @PostMapping("")
    public String addOrder(String taobaoOrderId, String customerTaobaoId, Integer postCardCount) {
        System.out.println(taobaoOrderId);
        System.out.println(customerTaobaoId);
        System.out.println(postCardCount);
        return "redirect:order/123423";
    }


    @GetMapping("")
    public String getOrderAll(Model model) {
        model.addAttribute("orders", orderService.getAllOrderEntity());
        return "order/order_list";
    }

    @RequiresPermissions("order:create")
    @GetMapping("new")
    public ModelAndView getCreateOrderPage(ModelAndView view) {
        view.setViewName("background/order/order_new");
        return view;
    }


    @GetMapping("{orderId}")
    public String getOrderDetail(@PathVariable("orderId") String orderId, Model model) {
        model.addAttribute("orderId", orderId);
        try {
            OrderEntity orderEntity = orderService.getOrderEntity(orderId);
            model.addAttribute("order", orderEntity);
            return "background/order/order_detail";
        } catch (NoSuchOrderException e) {
            //没有找到Order的情况下，跳转到订单列表
            return "redirect:.";
        }
    }

}
