package com.exchange.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * The interface File writer service.
 */
public interface FileWriterService {

    /**
     * Save file.
     *
     * @param multipartFile the multipart file
     * @param encodeName    the encode name
     */
    void saveFile(MultipartFile multipartFile, String encodeName);

}
