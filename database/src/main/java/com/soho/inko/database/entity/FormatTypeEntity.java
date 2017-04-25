package com.soho.inko.database.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by ZhongChongtao on 2017/4/3.
 */
@Entity
@Table(name = "crm_format_type", schema = "postcardtailor")
public class FormatTypeEntity {
    private String id;
    private String name;
    private Integer paddingTop;
    private Integer paddingLeft;
    private Integer paddingBottom;
    private Integer paddingRight;
    private Boolean ratable;
    private Double ratio;


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
    @Column(name = "PADDING_TOP")
    public Integer getPaddingTop() {
        return paddingTop;
    }

    public void setPaddingTop(Integer paddingTop) {
        this.paddingTop = paddingTop;
    }

    @Basic
    @Column(name = "PADDING_LEFT")
    public Integer getPaddingLeft() {
        return paddingLeft;
    }

    public void setPaddingLeft(Integer paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    @Basic
    @Column(name = "PADDING_BOTTOM")
    public Integer getPaddingBottom() {
        return paddingBottom;
    }

    public void setPaddingBottom(Integer paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    @Basic
    @Column(name = "PADDING_RIGHT")
    public Integer getPaddingRight() {
        return paddingRight;
    }

    public void setPaddingRight(Integer paddingRight) {
        this.paddingRight = paddingRight;
    }

    @Basic
    @Column(name = "RATABLE")
    public Boolean getRatable() {
        return ratable;
    }

    public void setRatable(Boolean ratable) {
        this.ratable = ratable;
    }

    @Basic
    @Column(name = "RATIO")
    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        FormatTypeEntity that = (FormatTypeEntity) object;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (paddingTop != null ? !paddingTop.equals(that.paddingTop) : that.paddingTop != null) return false;
        if (paddingLeft != null ? !paddingLeft.equals(that.paddingLeft) : that.paddingLeft != null) return false;
        if (paddingBottom != null ? !paddingBottom.equals(that.paddingBottom) : that.paddingBottom != null) return false;
        if (paddingRight != null ? !paddingRight.equals(that.paddingRight) : that.paddingRight != null) return false;
        if (ratable != null ? !ratable.equals(that.ratable) : that.ratable != null) return false;
        if (ratio != null ? !ratio.equals(that.ratio) : that.ratio != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (paddingTop != null ? paddingTop.hashCode() : 0);
        result = 31 * result + (paddingLeft != null ? paddingLeft.hashCode() : 0);
        result = 31 * result + (paddingBottom != null ? paddingBottom.hashCode() : 0);
        result = 31 * result + (paddingRight != null ? paddingRight.hashCode() : 0);
        result = 31 * result + (ratable != null ? ratable.hashCode() : 0);
        result = 31 * result + (ratio != null ? ratio.hashCode() : 0);
        return result;
    }
}
