package com.exchange.service;

import com.exchange.dao.File;

import java.util.List;

public interface FileService {

    List<File> getAllFiles();

    List<File> getAllFilesByUserId(Long userId);

    File getFileById(Long id);

    Long addFile(File file);

    void updateFile(File file);

    void deleteFile(Long id);
}
