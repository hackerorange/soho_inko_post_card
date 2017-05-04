package com.soho.inko.service;

import com.soho.inko.database.entity.FileEntity;

import java.io.File;

/**
 * Created by ZhongChongtao on 2017/4/30.
 */
public interface ProcessedFileService {
    /**
     * 添加新的文件到数据库
     *
     * @param file     新的文件
     * @param category 文件分类
     * @param extension
     * @return 文件实体
     */
    public FileEntity addNewFile(File file, String category, String extension);
}
