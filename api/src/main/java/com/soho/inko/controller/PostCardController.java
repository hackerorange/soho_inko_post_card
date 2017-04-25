package com.soho.inko.controller;

import com.alibaba.fastjson.JSON;
import com.soho.inko.database.entity.EnvelopeEntity;
import com.soho.inko.database.entity.FormatTypeEntity;
import com.soho.inko.database.entity.PostCardEntity;
import com.soho.inko.database.repository.EnvelopeRepository;
import com.soho.inko.database.repository.FormatTypeRepository;
import com.soho.inko.domain.PostCardInfoDTO;
import com.soho.inko.image.Size;
import com.soho.inko.service.IPostCardService;
import com.soho.inko.support.PostCardSupport;
import com.soho.inko.utils.TypeChecker;
import com.soho.inko.web.response.AbstractResponse;
import com.soho.inko.web.response.BodyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ZhongChongtao on 2017/4/3.
 */
@RestController
@CrossOrigin("*")
@RequestMapping("postcard")
public class PostCardController {

    private final IPostCardService postCardService;
    private final EnvelopeRepository envelopeRepository;
    private final FormatTypeRepository formatTypeRepository;

    private final JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    public PostCardController(IPostCardService postCardService, EnvelopeRepository envelopeRepository, FormatTypeRepository formatTypeRepository, @SuppressWarnings("SpringJavaAutowiringInspection") JmsMessagingTemplate jmsMessagingTemplate) {
        this.postCardService = postCardService;
        this.envelopeRepository = envelopeRepository;
        this.formatTypeRepository = formatTypeRepository;
        this.jmsMessagingTemplate = jmsMessagingTemplate;
    }

    @RequestMapping("next")
    public AbstractResponse getNextPostCardInfo(@RequestHeader("tokenId") String tokenId) {
        PostCardEntity nextPostCardNeedCrop = postCardService.getNextPostCardNeedCrop(tokenId);
        if (TypeChecker.isOrNull(nextPostCardNeedCrop)) {
            AbstractResponse abstractResponse = new BodyResponse<PostCardEntity>();
            abstractResponse.setCode(404);
            abstractResponse.setMessage("没有找到需要裁切的明信片");
            abstractResponse.setStatus("OK");
            return abstractResponse;
        }
        PostCardInfoDTO postCardInfoDTO = new PostCardInfoDTO();
        //获取信封
        EnvelopeEntity envelopeEntity = envelopeRepository.findOne(nextPostCardNeedCrop.getEnvelopeId());
        Assert.notNull(envelopeEntity, "没有找到信封");
        FormatTypeEntity formatTypeEntity = formatTypeRepository.findOne(envelopeEntity.getFormatTypeId());
        Assert.notNull(formatTypeEntity, "没有找到板式信息");
        //设置成品尺寸
        Size productSize = new Size(envelopeEntity.getProductWidth(), envelopeEntity.getProductHeight());
        postCardInfoDTO.setProductSize(productSize);
        //获取板式信息
        postCardInfoDTO.setPictureSize(
                PostCardSupport.getPictureAreaSizeByPostCardType(productSize, formatTypeEntity));
        postCardInfoDTO.setLocation(
                PostCardSupport.getPictureAreaLocationByPostCardType(productSize, formatTypeEntity)
        );
        postCardInfoDTO.setPostCardFileId(nextPostCardNeedCrop.getFileId());
        postCardInfoDTO.setPostCardId(nextPostCardNeedCrop.getId());
        postCardInfoDTO.setLocation(
                PostCardSupport.getPictureAreaLocationByPostCardType(productSize, formatTypeEntity)
        );
        BodyResponse<PostCardInfoDTO> postCardInfoDTOBodyResponse = new BodyResponse<>();
        postCardInfoDTOBodyResponse.setMessage("操作成功");
        postCardInfoDTOBodyResponse.setCode(200);
        postCardInfoDTOBodyResponse.setBody(postCardInfoDTO);
        return postCardInfoDTOBodyResponse;
    }

    @RequestMapping("submit")
    public AbstractResponse subMitPostCard(@RequestBody PostCardInfoDTO postCardInfoDTO,
                                           @RequestHeader("tokenId") String tokenId) {
        System.out.println("tokenId为" + tokenId);
        postCardService.updatePostCardInfo(postCardInfoDTO, tokenId);
        jmsMessagingTemplate.convertAndSend("postCardQueue", JSON.toJSONString(postCardInfoDTO));
        AbstractResponse abstractResponse = new BodyResponse<>();
        abstractResponse.setCode(200);
        abstractResponse.setMessage("操作成功");
        return abstractResponse;
    }

}
