package com.dropio.upload.application.port;

import java.io.InputStream;

/*
    UploadService direkt diske mi yazar, yoksa bir abstraction (port) mı çağırmalı?
    ABSTRACTION PORT ÇAĞIRMALI ...
 */
public interface FileStoragePort {

    void save(InputStream content,
              String directory,
              String filename);

    void delete(String directory,
                String filename);

}
