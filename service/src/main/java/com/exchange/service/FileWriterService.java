package com.exchange.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * The interface File writer service.
 */
public interface FileWriterService {

    /**
     * Save file.
     *
     * @param multipartFile the multipart file
     * @param encodeName    the encode name
     * @throws IOException the io exception
     */
    void saveFile(MultipartFile multipartFile, String encodeName) throws IOException;

    /**
     * Gets file by name.
     *
     * @param fileName the file name
     * @return the file by name
     */
    java.io.File getFileByName(String fileName);

    /**
     * Delete file by name.
     *
     * @param fileName the file name
     */
    void deleteFileByName(String fileName);

    /**
     * Delete files by names.
     *
     * @param fileNames the file names
     */
    void deleteFilesByNames(List<String> fileNames);
}
