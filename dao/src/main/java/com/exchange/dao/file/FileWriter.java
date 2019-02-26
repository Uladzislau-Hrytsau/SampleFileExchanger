package com.exchange.dao.file;

import org.springframework.web.multipart.MultipartFile;

/**
 * The interface File writer.
 */
public interface FileWriter {

    /**
     * Save file.
     *
     * @param multipartFile the multipart file
     * @param filePath      the file path
     */
    void saveFile(MultipartFile multipartFile, String filePath);

}
