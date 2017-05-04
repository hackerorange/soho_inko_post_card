package com.soho.inko.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ZhongChongtao on 2017/4/29.
 */
@Controller
@RequestMapping("background/order/{orderId}")
public class EnvelopeController {

    @GetMapping("new")
    public String createNewEnvelope(
            Model model,
            @PathVariable(name = "orderId") String orderId
    ) {
        model.addAttribute("orderId", orderId);
        return "background/envelope/envelope_new";
    }


    @GetMapping("{envelopeId}")
    public String getEnvelopeDetails(
            Model model,
            @PathVariable(name = "orderId") String orderId,
            @PathVariable(name = "envelopeId") String envelopeId
    ) {
        model.addAttribute("orderId", orderId);
        model.addAttribute("envelopeId", envelopeId);
        return "background/envelope/envelope_detail";
    }


}
