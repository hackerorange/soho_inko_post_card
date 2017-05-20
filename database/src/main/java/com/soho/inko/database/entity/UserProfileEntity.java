package com.soho.inko.database.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by ZhongChongtao on 2017/5/20.
 */
@Entity
@Table(name = "hrm_user_profile", schema = "postcardtailor")
public class UserProfileEntity {
    private String id;
    private String userId;
    private Integer iconUrl;
    private Integer userName;
    private Date createDate;
    private Date updateDate;
    private String remark;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(strategy = "uuid", name = "UUID")
    public String getId() {
        return id;
    }

    public UserProfileEntity setId(String id) {
        this.id = id;
        return this;
    }

    @Basic
    @Column(name = "USER_ID")
    public String getUserId() {
        return userId;
    }

    public UserProfileEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Basic
    @Column(name = "ICON_URL")
    public Integer getIconUrl() {
        return iconUrl;
    }

    public UserProfileEntity setIconUrl(Integer iconUrl) {
        this.iconUrl = iconUrl;
        return this;
    }

    @Basic
    @Column(name = "USER_NAME")
    public Integer getUserName() {
        return userName;
    }

    public UserProfileEntity setUserName(Integer userName) {
        this.userName = userName;
        return this;
    }

    @Basic
    @Column(name = "CREATE_DATE")
    public Date getCreateDate() {
        return createDate;
    }

    public UserProfileEntity setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    @Basic
    @Column(name = "UPDATE_DATE")
    public Date getUpdateDate() {
        return updateDate;
    }

    public UserProfileEntity setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    @Basic
    @Column(name = "REMARK")
    public String getRemark() {
        return remark;
    }

    public UserProfileEntity setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        UserProfileEntity that = (UserProfileEntity) object;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (iconUrl != null ? !iconUrl.equals(that.iconUrl) : that.iconUrl != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (updateDate != null ? !updateDate.equals(that.updateDate) : that.updateDate != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (iconUrl != null ? iconUrl.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}
