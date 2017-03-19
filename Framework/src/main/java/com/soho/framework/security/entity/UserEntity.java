package com.soho.framework.security.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ZhongChongtao on 2017/3/18.
 */
@Entity
@Table(name = "crm_user", schema = "postcardtailor", catalog = "")
public class UserEntity {
    private String id;
    private String taoBaoId;
    private String userName;
    private String password;
    private String salt;
    private String email;
    private Timestamp registDate;
    private String remark;
    private String updateAccountId;
    private Timestamp updateDate;

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
    @Column(name = "TAO_BAO_ID")
    public String getTaoBaoId() {
        return taoBaoId;
    }

    public void setTaoBaoId(String taoBaoId) {
        this.taoBaoId = taoBaoId;
    }

    @Basic
    @Column(name = "USER_NAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "SALT")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Basic
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "REGIST_DATE")
    public Timestamp getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Timestamp registDate) {
        this.registDate = registDate;
    }

    @Basic
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "UPDATE_ACCOUNT_ID")
    public String getUpdateAccountId() {
        return updateAccountId;
    }

    public void setUpdateAccountId(String updateAccountId) {
        this.updateAccountId = updateAccountId;
    }

    @Basic
    @Column(name = "UPDATE_DATE")
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (taoBaoId != null ? !taoBaoId.equals(that.taoBaoId) : that.taoBaoId != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (salt != null ? !salt.equals(that.salt) : that.salt != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (registDate != null ? !registDate.equals(that.registDate) : that.registDate != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (updateAccountId != null ? !updateAccountId.equals(that.updateAccountId) : that.updateAccountId != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (taoBaoId != null ? taoBaoId.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (registDate != null ? registDate.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (updateAccountId != null ? updateAccountId.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }

    @PrePersist
    public void prePersist() {
        this.registDate = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = new Timestamp(System.currentTimeMillis());
    }

}
