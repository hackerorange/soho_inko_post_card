package com.soho.inko.database.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by ZhongChongtao on 2017/4/27.
 */
@Entity
@Table(name = "sys_dictionary_param_map", schema = "postcardtailor")
public class DictionaryParamMapEntity {
    private String id;
    private String dictionaryId;
    private String dictionaryName;
    private String paramKey;
    private String paramValue;

    @Basic
    @Column(name = "DICTIONARY_NAME")
    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
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
    @Column(name = "DICTIONARY_ID")
    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    @Basic
    @Column(name = "PARAM_KEY")
    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    @Basic
    @Column(name = "PARAM_VALUE")
    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        DictionaryParamMapEntity that = (DictionaryParamMapEntity) object;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dictionaryId != null ? !dictionaryId.equals(that.dictionaryId) : that.dictionaryId != null) return false;
        if (paramKey != null ? !paramKey.equals(that.paramKey) : that.paramKey != null) return false;
        if (paramValue != null ? !paramValue.equals(that.paramValue) : that.paramValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {

        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dictionaryId != null ? dictionaryId.hashCode() : 0);
        result = 31 * result + (dictionaryId != null ? dictionaryId.hashCode() : 0);
        result = 31 * result + (paramKey != null ? paramKey.hashCode() : 0);
        result = 31 * result + (paramValue != null ? paramValue.hashCode() : 0);
        return result;
    }

}
