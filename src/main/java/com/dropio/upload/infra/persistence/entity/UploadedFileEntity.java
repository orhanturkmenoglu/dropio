package com.dropio.upload.infra.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Persistence entity: DB ile ilişkili
 * Domain entity’den ayrı tutulur
 */
@Entity
@Table(name = "uploaded_files")
public class UploadedFileEntity {

    @Id
    private UUID id;

    private String filename;
    private String contentType;
    private long size;
    private String storagePath;
    private LocalDateTime uploadedAt;

    protected UploadedFileEntity() {
        // JPA için default constructor
    }

    public UploadedFileEntity(UUID id,
                              String filename,
                              String contentType,
                              long size,
                              String storagePath,
                              LocalDateTime uploadedAt) {
        this.id = id;
        this.filename = filename;
        this.contentType = contentType;
        this.size = size;
        this.storagePath = storagePath;
        this.uploadedAt = uploadedAt;
    }

    // Getters
    public UUID getId() { return id; }
    public String getFilename() { return filename; }
    public String getContentType() { return contentType; }
    public long getSize() { return size; }
    public String getStoragePath() { return storagePath; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
}
