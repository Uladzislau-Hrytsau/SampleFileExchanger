package com.exchange.service;

import com.exchange.dao.File;
import org.springframework.web.multipart.MultipartFile;

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
     * @param file          the file
     * @param multipartFile the multipart file
     * @param userName      the user name
     * @return the long
     */
    Long addFile(File file, MultipartFile multipartFile, String userName);

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
