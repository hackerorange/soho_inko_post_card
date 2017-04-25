package com.soho.inko.database.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * Created by ZhongChongtao on 2017/3/29.
 */
@Entity
@Table(name = "dic_size", schema = "postcardtailor")
public class SizeEntity {
    private String id;
    @NotBlank(message = "尺寸名称不可为空")
    private String name;
    private Integer width;
    private Integer height;

    @Id
    @Column(name = "ID")
    @GenericGenerator(name = "UUID", strategy = "uuid")
    @GeneratedValue(generator = "UUID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "WIDTH")
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Basic
    @Column(name = "HEIGHT")
    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        SizeEntity that = (SizeEntity) object;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (width != null ? !width.equals(that.width) : that.width != null) return false;
        if (height != null ? !height.equals(that.height) : that.height != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (width != null ? width.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        return result;
    }

    @PrePersist
    public void prePersist() {
        //确保宽度一定大于长度，在保存的时候
        if (this.width < this.height) {
            Integer tmp = this.width;
            this.setWidth(this.getHeight());
            this.setHeight(tmp);
        }
    }

    @PreUpdate
    public void preUpdate() {
        //确保宽度一定大于长度，在更新的时候
        if (this.width < this.height) {
            Integer tmp = this.width;
            this.setWidth(this.getHeight());
            this.setHeight(tmp);
        }
    }
}
