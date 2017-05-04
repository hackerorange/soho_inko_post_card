package com.soho.inko.controller;

import com.soho.inko.service.IPostCardService;
import com.soho.inko.web.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ZhongChongtao on 2017/4/29.
 */
@RestController
@RequestMapping("order/{orderId}/{envelopeId}")
public class PostCardController {
    private final IPostCardService postCardService;

    @Autowired
    public PostCardController(IPostCardService postCardService) {
        this.postCardService = postCardService;
    }

    @DeleteMapping("{postCardId}")
    public StandardResponse deletePostCardEntity(@PathVariable(name = "postCardId") String postCardId) {
        StandardResponse standardResponse = new StandardResponse();
        if (postCardService.deletePostCard(postCardId)) {
            standardResponse.success();
        } else {
            standardResponse.failure();
        }
        return standardResponse;
    }

}
