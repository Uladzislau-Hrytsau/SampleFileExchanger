package com.exchange.dao;

import com.exchange.dto.file.FileStructureDto;
import com.exchange.dto.file.FileUpdatingDto;

import java.util.List;

/**
 * The interface File dao.
 */
public interface FileDao {

    /**
     * Gets files by limit and offset.
     *
     * @param limit  the limit
     * @param offset the offset
     * @return the files by limit and offset
     */
    List<File> getFilesByLimitAndOffset(Integer limit, Integer offset);

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
     * @param fileUpdatingDto the file updating dto
     * @return the integer
     */
    Integer updateFile(FileUpdatingDto fileUpdatingDto);

    /**
     * Delete file integer.
     *
     * @param id the id
     * @return the integer
     */
    Integer deleteFile(Long id);

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
