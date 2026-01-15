package com.dropio.upload.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Domain entity: DB bilmez, sadece business logic içerir.
 * Immutable ve framework bağımsızdır.
 */
public class UploadedFile {
    private final UUID id;
    private final String filename;
    private final String extension;
    private final String contentType;
    private final long size;
    private final String storagePath;
    private final LocalDateTime uploadedAt;

    public UploadedFile(UUID id, String filename,
                        String extension,
                        String contentType,
                        long size,
                        String storagePath,
                        LocalDateTime uploadedAt) {
        this.id = id;
        this.filename = filename;
        this.extension = extension;
        this.contentType = contentType;
        this.size = size;
        this.storagePath = storagePath;
        this.uploadedAt = uploadedAt;
    }

    /**
     * Factory method
     */
    public static UploadedFile create(
            String filename,
            String extension,
            String contentType,
            long size,
            String storagePath
    ){
        return new UploadedFile(
                UUID.randomUUID(),
                filename,
                extension,
                contentType,
                size,
                storagePath,
                LocalDateTime.now()
        );
    }

    // Getters
    public UUID getId() { return id; }
    public String getFilename() { return filename; }
    public String getExtension() { return extension; }
    public String getContentType() { return contentType; }
    public long getSize() { return size; }
    public String getStoragePath() { return storagePath; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }

    /*
        ✔️ Domain saf
        ✔️ JPA yok
        ✔️ Framework yok
        ✔️ Business object

        NEDEN BÖYLE ? :

        Entity kendini oluşturur

        ID üretimi domain kararı

        Immutable → bug azalır

        Test yazmak kolay
     */
}
