package com.soho.inko.service.impl;

import com.soho.inko.configuration.properties.FileProperties;
import com.soho.inko.entity.FileEntity;
import com.soho.inko.repository.FileRepository;
import com.soho.inko.service.FileService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;
/**
 * Created by ZhongChongtao on 2017/3/11.
 */
@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileProperties fileProperties;
    @Autowired
    public FileServiceImpl(FileRepository fileRepository, FileProperties fileProperties) {
        this.fileRepository = fileRepository;
        this.fileProperties = fileProperties;
    }
    @Override
    public com.soho.inko.entity.FileEntity uploadFile(MultipartFile file) throws IOException {
        String                          md5 = DigestUtils.md5Hex(file.getBytes());
        com.soho.inko.entity.FileEntity one = fileRepository.findOne(md5);
        // ���ݿ����Ѿ��д��ļ�
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
                    // noinspection ResultOfMethodCallIgnored
                    file_disk.delete();
                    file.transferTo(file_disk);
                }
                return one;
            }
        }
        // û���ҵ���¼�������µ��ļ�
        String   filePath = fileProperties.getFilePathTemplate();
        Calendar calendar = Calendar.getInstance();
        filePath = filePath.replace("#YEAR", calendar.get(Calendar.YEAR) + "");
        filePath = filePath.replace("#MONTH", calendar.get(Calendar.MONTH) + 1 + "");
        filePath = filePath.replace("#DAY", calendar.get(Calendar.DAY_OF_MONTH) + "");
        filePath = filePath.replace("#FILE_NAME", md5);
        File targetFile = new File(filePath);
        if (!targetFile.getParentFile().exists()) {
            // noinspection ResultOfMethodCallIgnored
            targetFile.getParentFile().mkdirs();
        }
        file.transferTo(new File(filePath));
        FileEntity fileEntity = new com.soho.inko.entity.FileEntity();
        fileEntity.setPath(filePath);
        fileEntity.setId(md5);
        fileEntity.setTitle(file.getOriginalFilename());
        fileRepository.save(fileEntity);
        return fileEntity;
    }
    @Override
    public File findById(String fileId) {
        FileEntity one = fileRepository.findOne(fileId);
        if (one == null) {
            return null;
        }
        File file = new File(one.getPath());
        return file;
    }
    @Override
    public FileEntity findFileEntityById(String fileId) {
        return fileRepository.findOne(fileId);
    }
}

