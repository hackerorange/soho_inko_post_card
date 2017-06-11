package com.soho.inko.database.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by ZhongChongtao on 2017/4/16.
 */
@Entity
@Table(name = "crm_user", schema = "postcardtailor")
public class UserEntity {
    private String id;
    private String realName;
    private String userName;
    private String userPassword = "_";
    private String salt;
    private Timestamp registDate;
    private String remark;
    private String updateAccountId;
    private Timestamp updateDate;
    private String email;
    private String skin;
    private boolean isLock = true;

    @Basic
    @Column(name = "SKIN")
    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    @Id
    @Column(name = "ID")
    @GenericGenerator(strategy = "uuid", name = "UUID")
    @GeneratedValue(generator = "UUID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "REAL_NAME")
    public String getRealName() {
        return realName;
    }

    public void setRealName(String taoBaoId) {
        this.realName = taoBaoId;
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
    @Column(name = "USER_PASSWORD")
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String password) {
        this.userPassword = password;
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

    @Basic
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "IS_LOCK")
    public boolean isIsLock() {
        return isLock;
    }

    public void setIsLock(boolean lock) {
        this.isLock = lock;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        UserEntity that = (UserEntity) object;

        if (isLock != that.isLock) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (realName != null ? !realName.equals(that.realName) : that.realName != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (userPassword != null ? !userPassword.equals(that.userPassword) : that.userPassword != null) return false;
        if (salt != null ? !salt.equals(that.salt) : that.salt != null) return false;
        if (registDate != null ? !registDate.equals(that.registDate) : that.registDate != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (updateAccountId != null ? !updateAccountId.equals(that.updateAccountId) : that.updateAccountId != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (realName != null ? realName.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        result = 31 * result + (registDate != null ? registDate.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (updateAccountId != null ? updateAccountId.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (isLock ? 1 : 0);
        return result;
    }

    @PrePersist
    public void prePersist() {

        this.setRegistDate(new Timestamp(System.currentTimeMillis()));

    }

}
