package com.soho.inko.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ZhongChongtao on 2017/3/26.
 */
@Mapper
public interface FileMapper {
    public String getFilePathByFileId(@Param("fileId") String fileId);
}
