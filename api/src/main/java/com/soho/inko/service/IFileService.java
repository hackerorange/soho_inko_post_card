package com.soho.inko.service;

import com.soho.inko.database.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by ZhongChongtao on 2017/3/11.
 */
public interface IFileService {
    /**
     * 上传文件
     *
     * @param file     multipartFile 文件
     * @param category 文件分类
     * @return 上传成功后，文件相关信息
     * @throws IOException 发生异常的时候，抛出异常
     */
    public FileEntity uploadFile(MultipartFile file, String category) throws IOException;

    /**
     * 根据fileId，获取原始文件
     *
     * @param fileId 文件ID
     * @return 原始文件
     */
    public File findById(String fileId);


    /**
     * 根据文件Id获取记录实体
     *
     * @param fileId 文件ID
     * @return 实体对象
     */
    public FileEntity findFileEntityById(String fileId);
}

