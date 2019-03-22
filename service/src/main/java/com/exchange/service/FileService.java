package com.exchange.service;

import com.exchange.dto.file.FileDto;
import com.exchange.dto.file.FileUpdatingDto;
import com.exchange.wrapper.Response;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

/**
 * The interface File service.
 */
public interface FileService {

    /**
     * Gets files and count by page and size.
     *
     * @param page           the page
     * @param size           the size
     * @param authentication the authentication
     * @return the files and count by page and size
     */
    Response getFilesAndCountByPageAndSize(Integer page, Integer size, Authentication authentication);

    /**
     * Add file long.
     *
     * @param fileDto        the file dto
     * @param multipartFile  the multipart file
     * @param authentication the authentication
     * @return the long
     */
    Long addFile(FileDto fileDto, MultipartFile multipartFile, Authentication authentication);

    /**
     * Update file.
     *
     * @param fileUpdatingDto the file updating dto
     */
    void updateFile(FileUpdatingDto fileUpdatingDto);

    /**
     * Delete file.
     *
     * @param id             the id
     * @param authentication the authentication
     */
    void deleteFile(Long id, Authentication authentication);

    /**
     * Gets file count.
     *
     * @return the file count
     */
    Long getFileCount();
}
