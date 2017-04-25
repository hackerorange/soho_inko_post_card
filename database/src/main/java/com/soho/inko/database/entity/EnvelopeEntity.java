package com.soho.inko.database.entity;

import com.soho.inko.database.constant.CropTypeEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ZhongChongtao on 2017/4/3.
 */
@Entity
@Table(name = "ppm_envelope", schema = "postcardtailor")
public class EnvelopeEntity {
    private String id;
    private String orderId;
    private String backgroundId;
    private String formatTypeId;
    private String managerId;
    private String cropperId;
    private Integer productWidth;
    private Integer productHeight;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String createBy;
    private String updateBy;
    private CropTypeEnum cropType;


    /**
     * 裁切类型
     *
     * @return 明信片裁切类型
     */
    @Column(name = "CROP_TYPE")
    @Enumerated(EnumType.STRING)
    public CropTypeEnum getCropType() {
        return cropType;
    }

    public void setCropType(CropTypeEnum cropType) {
        this.cropType = cropType;
    }


    @Basic
    @Column(name = "ORDER_ID")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "BACKGROUND_ID")
    public String getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(String backgroundId) {
        this.backgroundId = backgroundId;
    }

    @Basic
    @Column(name = "FORMAT_TYPE_ID")
    public String getFormatTypeId() {
        return formatTypeId;
    }

    public void setFormatTypeId(String formatTypeId) {
        this.formatTypeId = formatTypeId;
    }

    @Basic
    @Column(name = "MANAGER_ID")
    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    @Basic
    @Column(name = "CROPPER_ID")
    public String getCropperId() {
        return cropperId;
    }

    public void setCropperId(String cropperId) {
        this.cropperId = cropperId;
    }

    @Basic
    @Column(name = "PRODUCT_WIDTH")
    public Integer getProductWidth() {
        return productWidth;
    }

    public void setProductWidth(Integer productWidth) {
        this.productWidth = productWidth;
    }

    @Basic
    @Column(name = "PRODUCT_HEIGHT")
    public Integer getProductHeight() {
        return productHeight;
    }

    public void setProductHeight(Integer productHeight) {
        this.productHeight = productHeight;
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
    @Column(name = "UPDATE_AT")
    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
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
    @Column(name = "UPDATE_BY")
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        EnvelopeEntity that = (EnvelopeEntity) object;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (backgroundId != null ? !backgroundId.equals(that.backgroundId) : that.backgroundId != null) return false;
        if (formatTypeId != null ? !formatTypeId.equals(that.formatTypeId) : that.formatTypeId != null) return false;
        if (managerId != null ? !managerId.equals(that.managerId) : that.managerId != null) return false;
        if (cropperId != null ? !cropperId.equals(that.cropperId) : that.cropperId != null) return false;
        if (productWidth != null ? !productWidth.equals(that.productWidth) : that.productWidth != null) return false;
        if (productHeight != null ? !productHeight.equals(that.productHeight) : that.productHeight != null) return false;
        if (createAt != null ? !createAt.equals(that.createAt) : that.createAt != null) return false;
        if (updateAt != null ? !updateAt.equals(that.updateAt) : that.updateAt != null) return false;
        if (createBy != null ? !createBy.equals(that.createBy) : that.createBy != null) return false;
        if (updateBy != null ? !updateBy.equals(that.updateBy) : that.updateBy != null) return false;
        if (cropType != null ? !cropType.equals(that.cropType) : that.cropType != null) return false;
        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (backgroundId != null ? backgroundId.hashCode() : 0);
        result = 31 * result + (formatTypeId != null ? formatTypeId.hashCode() : 0);
        result = 31 * result + (managerId != null ? managerId.hashCode() : 0);
        result = 31 * result + (cropperId != null ? cropperId.hashCode() : 0);
        result = 31 * result + (productWidth != null ? productWidth.hashCode() : 0);
        result = 31 * result + (productHeight != null ? productHeight.hashCode() : 0);
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        result = 31 * result + (updateAt != null ? updateAt.hashCode() : 0);
        result = 31 * result + (createBy != null ? createBy.hashCode() : 0);
        result = 31 * result + (updateBy != null ? updateBy.hashCode() : 0);
        result = 31 * result + (cropType != null ? cropType.hashCode() : 0);
        result = 31 * result + (orderId != null ? orderId.hashCode() : 0);
        return result;
    }
}
