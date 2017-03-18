package com.soho.inko.service;

import com.soho.inko.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
/**
 * Created by ZhongChongtao on 2017/3/11.
 */
public interface FileService {
    public FileEntity uploadFile(MultipartFile file) throws IOException;
    public File findById(String fileId);
    public FileEntity findFileEntityById(String fileId);
}

