package com.dropio.upload.infra.storage;

import com.dropio.upload.application.port.FileStoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Local disk üzerinde dosya kaydeden adapter
 * Spring bean olarak @Component ile kullanılır
 */
@Component
public class LocalFileStorageAdapter implements FileStoragePort {

    private final Path rootPath;

    public LocalFileStorageAdapter(@Value("${storage.root-path}") String rootPathStr) {
        this.rootPath = Path.of(rootPathStr);
    }

    @Override
    public void save(InputStream content, String directory, String filename,String extension) {
        try{
            Path dirPath = rootPath.resolve(directory);
            Files.createDirectories(dirPath); // klasör yoksa oluştur

            Path target = dirPath.resolve(filename);
            // Eğer dosya varsa overwrite yapıyor
            Files.copy(content, target);

        }catch (IOException exception){
            throw new RuntimeException("File save failed",exception);
        }
    }

    @Override
    public void delete(String directory, String filename) {
        try{
            Path target = rootPath.resolve(directory).resolve(filename);
            Files.deleteIfExists(target);

        }catch (IOException exception){
            throw  new RuntimeException("File delete failed",exception);
        }
    }
}

/*
    ✔️ Infra
    ✔️ IO izolasyonu
    ✔️ Rollback hazırlığı
 */