package com.soho.inko.database.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soho.inko.database.constant.OrderStatusEnum;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ZhongChongtao on 2017/4/16.
 */
@Entity
@Table(name = "ppm_order", schema = "postcardtailor")
public class OrderEntity {
    private String id;
    private String customerAccountId;
    private String customerTaobaoId;
    private String createAccountId;
    private Timestamp createDate;
    private OrderStatusEnum orderStatus;

    @Basic
    @Column(name = "CUSTOMER_TAOBAO_ID")
    public String getCustomerTaobaoId() {
        return customerTaobaoId;
    }

    public void setCustomerTaobaoId(String customerTaobaoId) {
        this.customerTaobaoId = customerTaobaoId;
    }

    @Basic
    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.STRING)
    public OrderStatusEnum getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(strategy = "uuid", name = "UUID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "CUSTOMER_ACCOUNT_ID")
    public String getCustomerAccountId() {
        return customerAccountId;
    }

    public void setCustomerAccountId(String customerAccountId) {
        this.customerAccountId = customerAccountId;
    }

    @Basic
    @Column(name = "CREATE_ACCOUNT_ID")
    public String getCreateAccountId() {
        return createAccountId;
    }

    public void setCreateAccountId(String createAccountId) {
        this.createAccountId = createAccountId;
    }

    @Basic
    @Column(name = "CREATE_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        OrderEntity that = (OrderEntity) object;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (customerAccountId != null ? !customerAccountId.equals(that.customerAccountId) : that.customerAccountId != null) return false;
        if (createAccountId != null ? !createAccountId.equals(that.createAccountId) : that.createAccountId != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (customerTaobaoId != null ? !customerTaobaoId.equals(that.customerTaobaoId) : that.customerTaobaoId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (customerAccountId != null ? customerAccountId.hashCode() : 0);
        result = 31 * result + (createAccountId != null ? createAccountId.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (customerTaobaoId != null ? customerTaobaoId.hashCode() : 0);
        return result;
    }

    @PrePersist
    public void prePersist() {
        this.createDate = new Timestamp(System.currentTimeMillis());
        this.orderStatus = OrderStatusEnum.CREATE;
    }

}
