package com.soho.inko.controller;

import com.soho.inko.context.EnvelopeContext;
import com.soho.inko.database.entity.EnvelopeEntity;
import com.soho.inko.database.entity.PostCardEntity;
import com.soho.inko.service.IEnvelopeService;
import com.soho.inko.service.IPostCardService;
import com.soho.inko.utils.TypeChecker;
import com.soho.inko.web.response.BodyResponse;
import com.soho.inko.web.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ZhongChongtao on 2017/4/27.
 */
@RestController
@RequestMapping("order/{orderId}")
public class EnvelopeController {
    private final IPostCardService postCardService;
    private final IEnvelopeService envelopeService;

    @Autowired
    public EnvelopeController(IPostCardService postCardService, IEnvelopeService envelopeService) {
        this.postCardService = postCardService;
        this.envelopeService = envelopeService;
    }

    @PostMapping("{envelopeId}")
    public BodyResponse insertPostCardIntoEnvelope(
            @PathVariable(name = "envelopeId") String envelopeId,
            @RequestParam(name = "fileId") String fileId
    ) {
        BodyResponse<PostCardEntity> entityBodyResponse = new BodyResponse<>();
        PostCardEntity newPostCard = postCardService.createNewPostCard(envelopeId, fileId);
        //保存失败
        if (TypeChecker.isNull(newPostCard)) {
            entityBodyResponse.failure();
            return entityBodyResponse;
        }
        //保存成功
        entityBodyResponse
                .setBody(newPostCard)
                .success();
        return entityBodyResponse;
    }

    @DeleteMapping("{envelopeId}")
    public StandardResponse deleteEnvelope(@PathVariable(name = "envelopeId") String envelopeId) {
        StandardResponse bodyResponse = new StandardResponse();
        envelopeService.removeEnvelope(envelopeId);
        bodyResponse.success();
        return bodyResponse;
    }

    @GetMapping("{envelopeId}")
    public StandardResponse findByEnvelopeDetail(@PathVariable String envelopeId) {
        EnvelopeEntity envelope = envelopeService.findEnvelope(envelopeId);
        if (TypeChecker.isNull(envelope)) {
            StandardResponse standardResponse = new StandardResponse();
            standardResponse.failure();
            return standardResponse;
        }
        EnvelopeContext envelopeContext = new EnvelopeContext();
        BodyResponse<EnvelopeContext> envelopeContextBodyResponse = new BodyResponse<>();
        envelopeContext.setEnvelopeEntity(envelope);
        envelopeContext.setPostCardEntityList(postCardService.findAllByEnvelopeId(envelopeId));
        envelopeContextBodyResponse.setBody(envelopeContext);
        return envelopeContextBodyResponse;
    }

}
