package com.soho.inko.controller;

import com.soho.inko.context.EnvelopeContext;
import com.soho.inko.context.OrderContext;
import com.soho.inko.database.constant.CropTypeEnum;
import com.soho.inko.database.constant.OrderStatusEnum;
import com.soho.inko.database.entity.EnvelopeEntity;
import com.soho.inko.database.entity.OrderEntity;
import com.soho.inko.image.Size;
import com.soho.inko.service.IEnvelopeService;
import com.soho.inko.service.IOrderService;
import com.soho.inko.service.IPostCardService;
import com.soho.inko.utils.TypeChecker;
import com.soho.inko.web.response.AbstractResponse;
import com.soho.inko.web.response.BodyResponse;
import com.soho.inko.web.response.PageResultResponse;
import com.soho.inko.web.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhongChongtao on 2017/4/16.
 */
@RequestMapping("order")
@RestController
public class OrderController {

    private final IOrderService orderService;
    private final IEnvelopeService envelopeService;
    private final IPostCardService postCardService;

    @Autowired
    public OrderController(IOrderService orderService, IEnvelopeService envelopeService, IPostCardService postCardService) {
        this.orderService = orderService;
        this.envelopeService = envelopeService;
        this.postCardService = postCardService;
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

    /**
     * 获取指定ID的订单的详细信息
     *
     * @param orderId 订单ID
     * @return 此订单明细
     */
    @GetMapping(value = "{orderId}")
    public AbstractResponse getOrderDetail(@PathVariable(name = "orderId") String orderId) {
        OrderEntity orderEntity = orderService.findOrderById(orderId);
        if (TypeChecker.isNull(orderEntity)) {
            StandardResponse standardResponse = new StandardResponse();
            standardResponse.failure();
            return standardResponse;
        }
        OrderContext orderDetailContext = new OrderContext();
        orderDetailContext.setOrderEntity(orderEntity);
        List<EnvelopeEntity> envelopeEntities = envelopeService.findAllByOrderId(orderId);
        List<EnvelopeContext> envelopeContexts = new ArrayList<>();
        orderDetailContext.setEnvelopeEntities(envelopeContexts);
        for (EnvelopeEntity envelopeEntity : envelopeEntities) {
            EnvelopeContext envelopeContext = new EnvelopeContext();
            envelopeContext.setEnvelopeEntity(envelopeEntity);
            envelopeContexts.add(envelopeContext);
        }
        BodyResponse<OrderContext> bodyResponse = new BodyResponse<>();
        bodyResponse.setBody(orderDetailContext);
        return bodyResponse;
    }

    /**
     * 向指定的order中post对象
     *
     * @param cardBack   明信片反面ID
     * @param cardFormat 明信片板式ID
     * @param width      成品尺寸宽度
     * @param height     成品尺寸高度
     * @param cropType   裁切类型
     * @param orderId    订单ID
     * @return 创建的envelope信息
     */
    @PostMapping("{orderId}")
    public BodyResponse insertNewEnvelope(
            @RequestParam("cardBack") String cardBack,
            @RequestParam("cardFormat") String cardFormat,
            @RequestParam("width") Integer width,
            @RequestParam("height") Integer height,
            @RequestParam(name = "cropType", required = false, defaultValue = "CROP_AND_WHITE") CropTypeEnum cropType,
            @RequestParam("productFileName") String productFileName,
            @RequestParam("copy")Integer copy,
            @PathVariable String orderId) {

        BodyResponse<EnvelopeEntity> bodyResponse = new BodyResponse<>();
        Size productSize = new Size();
        productSize.setWidth(width);
        productSize.setHeight(height);
        EnvelopeEntity envelopeEntity = envelopeService.insertNewEnvelope(orderId, productSize, cardFormat, cardBack, cropType, productFileName, copy);
        bodyResponse.setBody(envelopeEntity);
        return bodyResponse;

    }


}
