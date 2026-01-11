package com.dropio.upload.infra.persistence.repository;

import com.dropio.upload.infra.persistence.entity.UploadedFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UploadedFileJpaRepository
        extends JpaRepository<UploadedFileEntity, UUID> {
}
