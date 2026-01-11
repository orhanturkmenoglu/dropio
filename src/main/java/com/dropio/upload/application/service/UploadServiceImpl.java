package com.dropio.upload.application.service;

import com.dropio.upload.application.command.UploadCommand;
import com.dropio.upload.application.port.FileStoragePort;
import com.dropio.upload.domain.entity.UploadedFile;
import com.dropio.upload.infra.persistence.entity.UploadedFileEntity;
import com.dropio.upload.infra.persistence.mapper.UploadedFileMapper;
import com.dropio.upload.infra.persistence.repository.UploadedFileJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UploadServiceImpl implements UploadService {

    private final FileStoragePort storagePort;
    private final UploadedFileJpaRepository uploadedFileJpaRepository;

    public UploadServiceImpl(FileStoragePort storagePort,
                             UploadedFileJpaRepository uploadedFileJpaRepository) {
        this.storagePort = storagePort;
        this.uploadedFileJpaRepository = uploadedFileJpaRepository;
    }

    @Override
    @Transactional
    public void upload(UploadCommand command) {
        String directory = "uploads";
        String filename = command.originalFilename();

        try {
            // 1. Dosyayı yaz temp alana
            storagePort.save(
                    command.content(),
                    directory,
                    filename
            );

            // 2. Metadata db yazılacak Domain entity oluştur

            UploadedFile uploadedFile = UploadedFile
                    .create(filename,
                            command.contentType(),
                            command.size(),
                            directory);

            // 3. DB KAYDET
            // mapper dönüşüm
            UploadedFileEntity uploadedFileEntity = UploadedFileMapper.toEntity(uploadedFile);
            uploadedFileJpaRepository.save(uploadedFileEntity);

        } catch (Exception exception) {
            // 4️⃣ Compensating action: DB başarısızsa veya disk hatası
            storagePort.delete(directory, filename);
            throw exception;
        }
    }
}

/*
    🔑 ÖNE ÇIKAN NOKTALAR

        Domain ↔ Infra ayrımı net

        Transactional sadece DB’ye uygulanıyor

        Disk IO compensation ile geri alınıyor

        Mapper kullanımı ile domain entity DB bağımlı değil

        Service temiz ve test edilebilir
 */