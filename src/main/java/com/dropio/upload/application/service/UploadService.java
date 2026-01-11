package com.dropio.upload.application.service;

import com.dropio.upload.application.command.UploadCommand;

public interface UploadService {

    void upload (UploadCommand command);
}
