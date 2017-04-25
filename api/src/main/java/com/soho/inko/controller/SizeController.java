package com.soho.inko.controller;

import com.soho.inko.database.entity.SizeEntity;
import com.soho.inko.service.ISizeService;
import com.soho.inko.web.response.AbstractResponse;
import com.soho.inko.web.response.BodyResponse;
import com.soho.inko.web.response.PageResultResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ZhongChongtao on 2017/3/29.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("dictionary/size")
public class SizeController {
    private final ISizeService sizeService;

    @Autowired
    public SizeController(ISizeService sizeService) {
        this.sizeService = sizeService;
    }

    @GetMapping({"", "/"})
    public AbstractResponse getSize() {
        List<SizeEntity> allSize = sizeService.getAllSize();
        PageResultResponse<SizeEntity> sizeEntityPageResultResponse = new PageResultResponse<>();
        sizeEntityPageResultResponse.setTotal(allSize.size());
        sizeEntityPageResultResponse.setMessage("操作成功");
        sizeEntityPageResultResponse.setBody(allSize);
        return sizeEntityPageResultResponse;
    }

    @PostMapping({"", "/"})
    public AbstractResponse insertSize(@RequestBody SizeEntity sizeEntity) {
        if (StringUtils.isEmpty(sizeEntity.getName())){
            BodyResponse bodyResponse=new BodyResponse();
            bodyResponse.setMessage("明信片名称不能为");
        }
        sizeEntity = sizeService.insertSize(sizeEntity.getName(), sizeEntity.getWidth(), sizeEntity.getHeight());
        BodyResponse<SizeEntity> sizeEntityBodyResponse = BodyResponse.newSuccessInstance(SizeEntity.class);
        sizeEntityBodyResponse.setBody(sizeEntity);
        return sizeEntityBodyResponse;
    }

    @DeleteMapping("{sizeId}")
    public AbstractResponse deleteSize(@PathVariable String sizeId) {
        sizeService.deleteSize(sizeId);
        BodyResponse bodyResponse = new BodyResponse();
        bodyResponse.setKey("I00000");
        bodyResponse.setMessage("操作成功");
        bodyResponse.setCode(200);
        return bodyResponse;
    }
}
