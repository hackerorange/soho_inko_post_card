package com.soho.inko.service.impl;

import com.soho.inko.configuration.properties.FileProperties;
import com.soho.inko.entity.FileEntity;
import com.soho.inko.repository.FileRepository;
import com.soho.inko.service.FileService;
import com.soho.inko.utils.PictureUtils;
import com.soho.inko.utils.TypeChecker;
import org.apache.commons.codec.digest.DigestUtils;
import org.im4java.core.IM4JavaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

/**
 * 图片文件服务类 Created by ZhongChongtao on 2017/3/11.
 */
@Service
public class ImageFileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final FileProperties fileProperties;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ImageFileServiceImpl(FileRepository fileRepository, FileProperties fileProperties) {
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
                    // noinspection ResultOfMethodCallIgnored
                    file_disk.delete();
                    file.transferTo(file_disk);
                }
                return one;
            }
        }
        String filePath = fileProperties.getFilePathTemplate();
        String thumbnailPath = "";
        Calendar calendar = Calendar.getInstance();
        filePath = filePath.replace("#YEAR", calendar.get(Calendar.YEAR) + "");
        filePath = filePath.replace("#MONTH", calendar.get(Calendar.MONTH) + 1 + "");
        filePath = filePath.replace("#DAY", calendar.get(Calendar.DAY_OF_MONTH) + "");
        // 正式文件的地址
        filePath = filePath.replace("#FILE_NAME",
                md5 + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        logger.info("文件要保存的路径为:" + filePath);
        // logger.info("文件缩略图的路径为:" + thumbnailPath);
        // File targetFile = new File(filePath);
        // if (!targetFile.getParentFile().exists()) {
        // logger.info("目录不存在，正在创建目录");
        // // noinspection ResultOfMethodCallIgnored
        // targetFile.getParentFile().mkdirs();
        // }
        // file.transferTo(new File(filePath));
        // try {
        // PictureUtils.zoomPicture(filePath, thumbnailPath, 200, 200);
        // } catch (InterruptedException | IM4JavaException e) {
        // logger.error("生成缩略图发生错误", e);
        // }
        // String thumbnailFilePath =
        // ImageThumbnailUtils.thumbnailImage(filePath, 200, 200);
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
    public File generateThumbnail(String fileId, Double rotation) {
        FileEntity fileEntity = fileRepository.findOne(fileId);
        String filePath = fileProperties.getTmpFilePathTemplate();
        filePath = filePath
                .replace("#FILE_NAME", fileEntity.getId())
                .replace("#ROTATION", rotation.toString());
        File tmpPictureFile = new File(filePath);
        if (!tmpPictureFile.getParentFile().exists()) {
            logger.info("目录不存在，正在创建目录");
            // noinspection ResultOfMethodCallIgnored
            tmpPictureFile.getParentFile().mkdirs();
        }
        if (tmpPictureFile.exists()) {
            return tmpPictureFile;
        }
        try {
            // 生成图片
            PictureUtils.zoomPicture(fileEntity.getPath(), filePath, 300, 300, rotation);
            if (!tmpPictureFile.exists()) {
                logger.info("执行完毕,但是没有找到图片");
                return null;
            }
            Map<String, String> imageInfo = PictureUtils.getImageInfo(filePath);
            if (TypeChecker.isNull(imageInfo)) {
                logger.info("无法获取缩略图的大小信息");
            } else {
                String width = imageInfo.get("width");
                String height = imageInfo.get("height");
                if (TypeChecker.isNumber(width) && TypeChecker.isNumber(height)) {
                    double pictureWidth = Double.parseDouble(width);
                    double pictureHeight = Double.parseDouble(height);
                    if (pictureHeight > pictureWidth) {
                        logger.info(fileId + "的宽度比高度小，正在生成旋转270度的照片");
                        generateThumbnail(fileId, (rotation + 270) % 360);
                    }
                }
            }
            return tmpPictureFile;
        } catch (InterruptedException | IM4JavaException | IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }


    @Override
    public FileEntity findFileEntityById(String fileId) {
        return fileRepository.findOne(fileId);
    }
}
