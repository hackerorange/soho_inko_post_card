package com.soho.inko.service.impl;

import com.soho.inko.database.entity.FileEntity;
import com.soho.inko.database.repository.FileRepository;
import com.soho.inko.service.ProcessedFileService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by ZhongChongtao on 2017/4/30.
 */
@Service
public class ProcessedFileServiceImpl implements ProcessedFileService {
    private final FileRepository fileRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProcessedFileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public FileEntity addNewFile(File file, String category, String extension) {
        if (!file.exists()) {
            return null;
        }
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            String diskFileMd5 = DigestUtils.md5Hex(fileInputStream);


            if (!extension.startsWith(".")) {
                extension = "." + extension;
            }
            File target = new File(file.getParent(), diskFileMd5 + extension);
            if (target.exists()) {
                logger.error("文件MD5值相同");
            }
            if (fileRepository.exists(diskFileMd5)) {
                logger.error("数据库中已经存在此Md5的对象");
                return null;
            }
            //重命名到目标文件名称
            //noinspection ResultOfMethodCallIgnored
            FileCopyUtils.copy(file, target);
            FileEntity fileEntity = new FileEntity();
            fileEntity.setCategory(category);
            fileEntity.setFileType(extension.substring(1));
            fileEntity.setTitle(file.getName());
            fileEntity.setPath(target.getAbsolutePath());
            fileEntity.setId(diskFileMd5);
            return fileRepository.save(fileEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
