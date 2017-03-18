package com.soho.inko.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.util.Date;
import javax.persistence.*;
/**
 * Created by ZhongChongtao on 2017/3/11.
 */
@Entity
@Table(
    name    = "res_file",
    schema  = "postcardtailor",
    catalog = ""
)
public class FileEntity {
    private String id;
    private String title;
    private String path;
    private Date   uploadDate;
    @Id
    @Column(name = "ID")
//  @GeneratedValue(generator = "UUID")
//  @GenericGenerator(name = "UUID", strategy = "uuid")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    @Basic
    @Column(name = "TITLE")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Basic
    @Column(name = "PATH")
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    @Basic
    @JsonIgnore
    @JsonFormat(
        timezone = "GMT+8",
        pattern  = "yyyy-MM-dd HH:mm:ss"
    )
    @Column(name = "UPLOAD_DATE")
    public Date getUploadDate() {
        return uploadDate;
    }
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        FileEntity that = (FileEntity) o;
        if ((id != null)
            ? !id.equals(that.id)
            : that.id != null) {
            return false;
        }
        if ((title != null)
            ? !title.equals(that.title)
            : that.title != null) {
            return false;
        }
        if ((path != null)
            ? !path.equals(that.path)
            : that.path != null) {
            return false;
        }
        if ((uploadDate != null)
            ? !uploadDate.equals(that.uploadDate)
            : that.uploadDate != null) {
            return false;
        }
        return true;
    }
    @Override
    public int hashCode() {
        int result = (id != null)
                     ? id.hashCode()
                     : 0;
        result = 31 * result + ((title != null)
                                ? title.hashCode()
                                : 0);
        result = 31 * result + ((path != null)
                                ? path.hashCode()
                                : 0);
        result = 31 * result + ((uploadDate != null)
                                ? uploadDate.hashCode()
                                : 0);
        return result;
    }
    @PrePersist
    public void prePersist() {
        this.setUploadDate(new Date(System.currentTimeMillis()));
    }
}

