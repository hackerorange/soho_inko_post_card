package com.soho.inko.service.impl;

import com.soho.inko.configuration.FileProperties;
import com.soho.inko.database.entity.FileEntity;
import com.soho.inko.database.repository.FileRepository;
import com.soho.inko.service.IFileService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

/**
 * 图片文件服务类 Created by ZhongChongtao on 2017/3/11.
 */
@Service
public class FileServiceImpl implements IFileService {
    private final FileRepository fileRepository;
    private final FileProperties fileProperties;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FileServiceImpl(FileRepository fileRepository, FileProperties fileProperties) {
        this.fileRepository = fileRepository;
        this.fileProperties = fileProperties;
    }

    @Override
    public FileEntity uploadFile(MultipartFile file, String category) throws IOException {
        String md5 = DigestUtils.md5Hex(file.getBytes());
        FileEntity one = fileRepository.findOne(md5);
        if (one != null) {
            File file_disk = new File(one.getPath());
            if (file_disk.exists()) {
                String diskFileMd5 = "";
                try (FileInputStream fileInputStream = new FileInputStream(file_disk)) {
                    diskFileMd5 = DigestUtils.md5Hex(fileInputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!Objects.equals(diskFileMd5, md5)) {
                    logger.info("从数据库中找到对应的记录，但是MD5不对应，替换旧文件");
                    // noinspection ResultOfMethodCallIgnored
                    file_disk.delete();
                    file.transferTo(file_disk);
                }
                return one;
            }
        }
        logger.info("从数据库中没有找到找到对应的记录，正在传输文件");
        String filePath = fileProperties.getFilePathTemplate();
        Calendar calendar = Calendar.getInstance();
        filePath = filePath.replace("#YEAR", calendar.get(Calendar.YEAR) + "");
        filePath = filePath.replace("#MONTH", calendar.get(Calendar.MONTH) + 1 + "");
        filePath = filePath.replace("#DAY", calendar.get(Calendar.DAY_OF_MONTH) + "");
        // 正式文件的地址
        filePath = filePath.replace("#FILE_NAME", md5 + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        logger.info("文件要保存的路径为:" + filePath);
        // 正式文件的地址
        logger.info("文件要保存的路径为:" + filePath);
        File targetFile = new File(filePath);
        //如果文件目录不存在，创建目录
        if (!targetFile.getParentFile().exists()) {
            logger.info("目录不存在，正在创建目录");
            // noinspection ResultOfMethodCallIgnored
            targetFile.getParentFile().mkdirs();
        }
        //传输文件
        file.transferTo(new File(filePath));
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPath(filePath);
        fileEntity.setId(md5);
        fileEntity.setTitle(file.getOriginalFilename());
        fileEntity.setCategory(category);
        fileRepository.save(fileEntity);
        return fileEntity;
    }

    @Override
    public File findById(String fileId) {
        FileEntity fileEntity = fileRepository.findOne(fileId);
        if (fileEntity == null) {
            return null;
        }
        return new File(fileEntity.getPath());
    }

    @Override
    public FileEntity findFileEntityById(String fileId) {
        return fileRepository.findOne(fileId);
    }
}
