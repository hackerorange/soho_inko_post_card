package com.soho.inko.context;

import com.soho.inko.database.entity.EnvelopeEntity;
import com.soho.inko.database.entity.PostCardEntity;

import java.util.List;

/**
 * Created by ZhongChongtao on 2017/4/28.
 */
public class EnvelopeContext {

    private EnvelopeEntity envelopeEntity;

    private List<PostCardEntity> postCardEntityList;

    public EnvelopeEntity getEnvelopeEntity() {
        return envelopeEntity;
    }

    public void setEnvelopeEntity(EnvelopeEntity envelopeEntity) {
        this.envelopeEntity = envelopeEntity;
    }

    public List<PostCardEntity> getPostCardEntityList() {
        return postCardEntityList;
    }

    public void setPostCardEntityList(List<PostCardEntity> postCardEntityList) {
        this.postCardEntityList = postCardEntityList;
    }
}
