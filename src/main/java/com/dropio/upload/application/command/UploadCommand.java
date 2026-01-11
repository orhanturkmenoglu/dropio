package com.dropio.upload.application.command;

import java.io.InputStream;

/*UPLOAD COMMAND : SERVİCE KATMANINA GİREN SAF VERİ*/
public record UploadCommand (InputStream content,
                             String originalFilename,
                             String contentType,
                             long size){
}
