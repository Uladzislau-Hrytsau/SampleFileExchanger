package com.exchange.service;

import com.exchange.dao.File;
import com.exchange.dto.StructureDto;
import com.exchange.wrapper.Response;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * The interface File service.
 */
public interface FileService {

    /**
     * Gets files by page and size.
     *
     * @param page the page
     * @param size the size
     * @return the files by page and size
     */
    Response getFilesAndCountByPageAndSize(Integer page, Integer size);

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
     * @param jsonFile       the json file
     * @param multipartFile  the multipart file
     * @param authentication the authentication
     * @return the long
     * @throws IOException the io exception
     */
    Long addFile(String jsonFile, MultipartFile multipartFile, Authentication authentication) throws IOException;

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

    /**
     * Gets all files and folders by folder id.
     *
     * @param authentication the authentication
     * @param folderId       the folder id
     * @return the all files and folders by folder id
     */
    StructureDto getAllFilesAndFoldersByFolderId(Authentication authentication, Long folderId);

    /**
     * Gets file count.
     *
     * @return the file count
     */
    Long getFileCount();
}
