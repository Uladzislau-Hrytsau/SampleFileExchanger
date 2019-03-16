package com.exchange.dao;

import com.exchange.dto.FileStructureDto;

import java.util.List;

/**
 * The interface File dao.
 */
public interface FileDao {

    /**
     * Gets all files by user id.
     *
     * @param userId the user id
     * @return the all files by user id
     */
    List<File> getAllFilesByUserId(Long userId);

    /**
     * Gets files by limit and offset.
     *
     * @param limit  the limit
     * @param offset the offset
     * @return the files by limit and offset
     */
    List<File> getFilesByLimitAndOffset(Integer limit, Integer offset);

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
     * Update file integer.
     *
     * @param file the file
     * @return the integer
     */
    Integer updateFile(File file);

    /**
     * Delete file integer.
     *
     * @param id the id
     * @return the integer
     */
    Integer deleteFile(Long id);

    /**
     * Check file by id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    Boolean checkFileById(Long id);

    /**
     * Check file by user id boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    Boolean checkFileByUserId(Long userId);

    /**
     * Check file by url boolean.
     *
     * @param url the url
     * @return the boolean
     */
    Boolean checkFileByUrl(String url);

    /**
     * Exists by encode name boolean.
     *
     * @param encodeName the encode name
     * @return the boolean
     */
    Boolean existsByEncodeName(String encodeName);

    /**
     * Gets all files by user id and folder id.
     *
     * @param userId   the user id
     * @param folderId the folder id
     * @return the all files by user id and folder id
     */
    List<FileStructureDto> getAllFilesByUserIdAndFolderId(Long userId, Long folderId);

    /**
     * Gets file count.
     *
     * @return the file count
     */
    Long getFileCount();
}
