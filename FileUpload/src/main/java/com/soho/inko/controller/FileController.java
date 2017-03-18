package com.soho.inko.controller;

import com.soho.framework.web.response.BodyResponse;
import com.soho.inko.entity.FileEntity;
import com.soho.inko.service.FileService;
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
    public BodyResponse<FileEntity> uploadFile(@RequestPart(name = "file") MultipartFile multipartFile) throws IOException {
        FileEntity fileEntity = fileService.uploadFile(multipartFile);
        BodyResponse<FileEntity> response = new BodyResponse<>();
        response.setBody(fileEntity);
        return response;
    }

    @GetMapping("{fileId}")
    public ResponseEntity<byte[]> fileDownload(@PathVariable("fileId") String fileId) throws IOException {
        File file = fileService.findById(fileId);
        FileEntity fileEntity = fileService.findFileEntityById(fileId);
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", new String(fileEntity.getTitle().getBytes("UTF-8"),
                "ISO8859-1"));
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
    }
}

