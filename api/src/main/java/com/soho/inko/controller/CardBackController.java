package com.soho.inko.controller;

import com.soho.inko.database.entity.CardBackgroundEntity;
import com.soho.inko.service.ICardBackgroundService;
import com.soho.inko.service.cache.ICardBackgroundCacheService;
import com.soho.inko.web.response.AbstractResponse;
import com.soho.inko.web.response.BodyResponse;
import com.soho.inko.web.response.PageResultResponse;
import com.soho.inko.web.response.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ZhongChongtao on 2017/4/27.
 */
@RestController
@RequestMapping("dictionary/cardBack")
public class CardBackController {
    private final ICardBackgroundService cardBackgroundService;
    private final ICardBackgroundCacheService cardBackgroundCacheService;

    @Autowired
    public CardBackController(ICardBackgroundService cardBackgroundService, ICardBackgroundCacheService cardBackgroundCacheService) {
        this.cardBackgroundService = cardBackgroundService;
        this.cardBackgroundCacheService = cardBackgroundCacheService;
    }


    @GetMapping("")
    public AbstractResponse getAllCardBackground() {
        PageResultResponse<CardBackgroundEntity> cardBackgroundEntityBodyResponse = new PageResultResponse<>();
        cardBackgroundEntityBodyResponse.setBody(cardBackgroundCacheService.findAllAvailableCardBackgroundEntity());
        return cardBackgroundEntityBodyResponse;
    }

    @PostMapping("")
    public AbstractResponse createNewCardBackground(
            @RequestParam("fileId") String fileId,
            @RequestParam("name") String title,
            @RequestHeader(name = "tokenId", required = false, defaultValue = "test") String tokenId) {
        CardBackgroundEntity background = cardBackgroundService.createBackground(title, fileId, tokenId);
        BodyResponse<CardBackgroundEntity> bodyResponse = new BodyResponse<>();
        bodyResponse.setBody(background);
        return bodyResponse;
    }

    @GetMapping("{backId}")
    public BodyResponse getCardBack(@PathVariable("backId") String backId) {
        BodyResponse<CardBackgroundEntity> bodyResponse = new BodyResponse<>();
        CardBackgroundEntity cardBackgroundEntityById = cardBackgroundCacheService.findCardBackgroundEntityByDictId(backId);
        bodyResponse.setBody(cardBackgroundEntityById).success();
        return bodyResponse;
    }

    @DeleteMapping("{title}")
    public StandardResponse deleteCardBack(@PathVariable("title") String title) {
        StandardResponse standardResponse = new StandardResponse();
        if (cardBackgroundService.deleteBackground(title)) {
            standardResponse.success();
        } else {
            standardResponse.failure();
        }
        return standardResponse;
    }
}
