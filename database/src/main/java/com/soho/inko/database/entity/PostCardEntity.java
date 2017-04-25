package com.soho.inko.database.entity;

import com.soho.inko.database.constant.PostCardProcessStatus;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ZhongChongtao on 2017/4/3.
 */
@Entity
@Table(name = "ppm_post_card", schema = "postcardtailor", catalog = "")
public class PostCardEntity {
    private String id;
    private String envelopeId;
    private String fileId;
    private Integer rotation;
    private Double cropLeft;
    private Double cropTop;
    private Double cropWidth;
    private Double cropHeight;
    private String cropBy;
    private Timestamp createAt;
    private String createBy;
    private Timestamp updateAt;
    private String updateBy;
    private PostCardProcessStatus processStatus;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ENVELOPE_ID")
    public String getEnvelopeId() {
        return envelopeId;
    }

    public void setEnvelopeId(String envelopeId) {
        this.envelopeId = envelopeId;
    }

    @Basic
    @Column(name = "FILE_ID")
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Basic
    @Column(name = "ROTATION")
    public Integer getRotation() {
        return rotation;
    }

    public void setRotation(Integer rotation) {
        this.rotation = rotation;
    }

    @Basic
    @Column(name = "CROP_LEFT")
    public Double getCropLeft() {
        return cropLeft;
    }

    public void setCropLeft(Double cropLeft) {
        this.cropLeft = cropLeft;
    }

    @Basic
    @Column(name = "CROP_TOP")
    public Double getCropTop() {
        return cropTop;
    }

    public void setCropTop(Double cropTop) {
        this.cropTop = cropTop;
    }

    @Basic
    @Column(name = "CROP_WIDTH")
    public Double getCropWidth() {
        return cropWidth;
    }

    public void setCropWidth(Double cropWidth) {
        this.cropWidth = cropWidth;
    }

    @Basic
    @Column(name = "CROP_HEIGHT")
    public Double getCropHeight() {
        return cropHeight;
    }

    public void setCropHeight(Double cropHeight) {
        this.cropHeight = cropHeight;
    }

    @Basic
    @Column(name = "CROP_BY")
    public String getCropBy() {
        return cropBy;
    }

    public void setCropBy(String cropBy) {
        this.cropBy = cropBy;
    }

    @Basic
    @Column(name = "CREATE_AT")
    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    @Basic
    @Column(name = "CREATE_BY")
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Basic
    @Column(name = "UPDATE_AT")
    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    @Basic
    @Column(name = "UPDATE_BY")
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Basic
    @Column(name = "PROCESS_STATUS")
    @Enumerated(EnumType.STRING)
    public PostCardProcessStatus getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(PostCardProcessStatus processStatus) {
        this.processStatus = processStatus;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        PostCardEntity that = (PostCardEntity) object;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (envelopeId != null ? !envelopeId.equals(that.envelopeId) : that.envelopeId != null) return false;
        if (fileId != null ? !fileId.equals(that.fileId) : that.fileId != null) return false;
        if (rotation != null ? !rotation.equals(that.rotation) : that.rotation != null) return false;
        if (cropLeft != null ? !cropLeft.equals(that.cropLeft) : that.cropLeft != null) return false;
        if (cropTop != null ? !cropTop.equals(that.cropTop) : that.cropTop != null) return false;
        if (cropWidth != null ? !cropWidth.equals(that.cropWidth) : that.cropWidth != null) return false;
        if (cropHeight != null ? !cropHeight.equals(that.cropHeight) : that.cropHeight != null) return false;
        if (cropBy != null ? !cropBy.equals(that.cropBy) : that.cropBy != null) return false;
        if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null) return false;
        if (createBy != null ? !createBy.equals(that.createBy) : that.createBy != null) return false;
        if (updateAt != null ? !updateAt.equals(that.updateAt) : that.updateAt != null) return false;
        if (updateBy != null ? !updateBy.equals(that.updateBy) : that.updateBy != null) return false;
        if (processStatus != null ? !processStatus.equals(that.processStatus) : that.processStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (envelopeId != null ? envelopeId.hashCode() : 0);
        result = 31 * result + (fileId != null ? fileId.hashCode() : 0);
        result = 31 * result + (rotation != null ? rotation.hashCode() : 0);
        result = 31 * result + (cropLeft != null ? cropLeft.hashCode() : 0);
        result = 31 * result + (cropTop != null ? cropTop.hashCode() : 0);
        result = 31 * result + (cropWidth != null ? cropWidth.hashCode() : 0);
        result = 31 * result + (cropHeight != null ? cropHeight.hashCode() : 0);
        result = 31 * result + (cropBy != null ? cropBy.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        result = 31 * result + (createBy != null ? createBy.hashCode() : 0);
        result = 31 * result + (updateAt != null ? updateAt.hashCode() : 0);
        result = 31 * result + (updateBy != null ? updateBy.hashCode() : 0);
        result = 31 * result + (processStatus != null ? processStatus.hashCode() : 0);
        return result;
    }


    @PrePersist
    public void prePersist() {
        this.createAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void preUpdate() {
        this.updateAt = new Timestamp(System.currentTimeMillis());
    }
}
