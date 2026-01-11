package com.dropio.upload.api.controller;

import com.dropio.upload.application.command.UploadCommand;
import com.dropio.upload.application.service.UploadService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {

    private final UploadService uploadService;

    public UploadController(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @PostMapping
    public ResponseEntity<Void> upload(@RequestPart("file") @NotNull MultipartFile file)
            throws IOException {
        UploadCommand command = new UploadCommand(
                file.getInputStream(),
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize()
        );

        uploadService.upload(command);

        return ResponseEntity.ok().build();
    }

    /*
        Controller multipartfile bilir,
        service bilmez.
     */
}
