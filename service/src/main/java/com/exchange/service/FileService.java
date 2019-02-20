package com.exchange.service;

import com.exchange.dao.File;

import java.util.List;

/**
 * The interface File service.
 */
public interface FileService {

    /**
     * Gets all files.
     *
     * @return the all files
     */
    List<File> getAllFiles();

    /**
     * Gets all files by user id.
     *
     * @param userId the user id
     * @return the all files by user id
     */
    List<File> getAllFilesByUserId(Long userId);

    /**
     * Gets file by id.
     *
     * @param id the id
     * @return the file by id
     */
    File getFileById(Long id);

    /**
     * Add file long.
     *
     * @param file the file
     * @return the long
     */
    Long addFile(File file);

    /**
     * Update file.
     *
     * @param file the file
     */
    void updateFile(File file);

    /**
     * Delete file.
     *
     * @param id the id
     */
    void deleteFile(Long id);
}
