package com.exchange.dao;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * The interface File writer.
 */
public interface FileWriter {

    /**
     * Save file.
     *
     * @param multipartFile the multipart file
     * @param filePath      the file path
     * @throws IOException the io exception
     */
    void saveFile(MultipartFile multipartFile, String filePath) throws IOException;

    /**
     * Delete file by path boolean.
     *
     * @param path the path
     * @return the boolean
     */
    Boolean deleteFileByPath(String path);

    /**
     * Gets file by name.
     *
     * @param fileName the file name
     * @return the file by name
     */
    File getFileByName(String fileName);

}
