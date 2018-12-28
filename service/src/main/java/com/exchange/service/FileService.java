package com.exchange.service;

import com.exchange.dao.File;

import java.time.LocalDate;
import java.util.List;

/**
 * File Service interface.
 * Created by Uladzislau Hrytsau on 1.12.18.
 */
public interface FileService {

    /**
     * Get all files list by user-id.
     *
     * @param userId user identifier.
     * @return file.
     */
    List<File> getAllFilesByUserId(Long userId);

    /**
     * Get all files list.
     *
     * @return file.
     */
    List<File> getAllFiles();

    /**
     * Get file by id.
     *
     * @param id user identifier.
     * @return file.
     */
    File getFileById(Long id);

    /**
     * Create new file.
     *
     * @param file user file
     * @return new file id.
     */
    Long addFile(File file);

    /**
     * Update file.
     *
     * @param file user file.
     */
    void updateFile(File file);

    /**
     * Delete file.
     *
     * @param id file identifier.
     */
    void deleteFile(Long id);
}
