package com.soho.inko.controller;

import com.soho.inko.database.constant.OrderStatusEnum;
import com.soho.inko.database.entity.OrderEntity;
import com.soho.inko.service.IOrderService;
import com.soho.inko.web.response.BodyResponse;
import com.soho.inko.web.response.PageResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ZhongChongtao on 2017/4/16.
 */
@RequestMapping("order")
@RestController
public class OrderController {

    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "")
    public BodyResponse<OrderEntity> createNewOrder(@RequestParam("taobaoId") String taobaoId, @RequestHeader(name = "tokenId") String tokenId) {
        OrderEntity order = orderService.createOrder(taobaoId, tokenId);
        BodyResponse<OrderEntity> orderEntityBodyResponse = new BodyResponse<>();
        orderEntityBodyResponse.setBody(order);
        orderEntityBodyResponse.setMessage("操作成功");
        orderEntityBodyResponse.setCode(200);
        return orderEntityBodyResponse;
    }

    @GetMapping(value = "")
    public PageResultResponse<OrderEntity> getAllOrder(@RequestParam(required = false, defaultValue = "CREATE") OrderStatusEnum orderStatusEnum) {
        System.out.println(orderStatusEnum.ordinal());
        List<OrderEntity> allByStatus = orderService.findAllByStatus(orderStatusEnum);
        PageResultResponse<OrderEntity> pageResultResponse = new PageResultResponse<>();
        pageResultResponse.setCode(200);
        pageResultResponse.setMessage("操作成功");
        pageResultResponse.setBody(allByStatus);
        return pageResultResponse;
    }

    @DeleteMapping(value = "{orderId}")
    public BodyResponse<Boolean> deleteOrder(@PathVariable(name = "orderId") String orderId, @RequestHeader String tokenId) {
        System.out.println(tokenId);
        return new BodyResponse<>(orderService.deleteByOrderId(orderId));
    }


}
