package com.dropio.upload.infra.persistence.mapper;

import com.dropio.upload.domain.entity.UploadedFile;
import com.dropio.upload.infra.persistence.entity.UploadedFileEntity;

public class UploadedFileMapper {

    public static UploadedFileEntity toEntity(UploadedFile domain) {
        return new UploadedFileEntity(
                domain.getId(),
                domain.getFilename(),
                domain.getExtension(),
                domain.getContentType(),
                domain.getSize(),
                domain.getStoragePath(),
                domain.getUploadedAt()
        );
    }
}
