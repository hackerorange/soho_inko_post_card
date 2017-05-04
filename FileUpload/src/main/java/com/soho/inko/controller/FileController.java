package com.soho.inko.controller;

import com.soho.inko.database.entity.FileEntity;
import com.soho.inko.service.FileService;
import com.soho.inko.utils.TypeChecker;
import com.soho.inko.web.response.BodyResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


/**
 * Created by ZhongChongtao on 2017/3/11.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("file")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("upload")
    public BodyResponse<FileEntity> uploadFile(@RequestPart(name = "file") MultipartFile multipartFile,
                                               @RequestParam(required = false, defaultValue = "", name = "category") String category,
                                               @RequestParam(required = false, defaultValue = "", name = "type") String type) throws IOException {
        FileEntity fileEntity = fileService.uploadFile(multipartFile, category, type);
        BodyResponse<FileEntity> response = new BodyResponse<>();
        response.setBody(fileEntity);
        return response;
    }

    @GetMapping("{fileId}")
    public ResponseEntity<byte[]> fileDownload(
            @PathVariable("fileId") String fileId,
            @RequestParam(required = false, defaultValue = "false", name = "isOriginal") Boolean isOriginal,
            @RequestParam(required = false, defaultValue = "0", name = "rotation") Double rotation) throws IOException {

        File file;
        if (isOriginal) {
            file = fileService.findById(fileId);
        } else {
            file = fileService.generateThumbnail(fileId, rotation);
        }

        FileEntity fileEntity = fileService.findFileEntityById(fileId);
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(fileEntity.getTitle().getBytes("UTF-8"), "ISO8859-1"));
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
    }

    @GetMapping("generateThumbnail/{fileId}")
    public String generateThumbnail(@PathVariable String fileId) {
        File thumbnailById = fileService.generateThumbnail(fileId, 0D);
        if (TypeChecker.isNull(thumbnailById)) {
            return "FAILURE";
        }
        return "SUCCESS";
    }

}

