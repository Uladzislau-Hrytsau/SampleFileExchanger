package com.exchange.dao;

import com.exchange.dto.file.FileDto;
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
     * @param fileDto the file dto
     * @return the long
     */
    Long addFile(FileDto fileDto);

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
     * @param fileId the file id
     * @param userId the user id
     * @return the integer
     */
    Integer deleteFile(Long fileId, Long userId);

    /**
     * Gets files by user id and folder id.
     *
     * @param userId   the user id
     * @param folderId the folder id
     * @return the files by user id and folder id
     */
    List<FileStructureDto> getFilesByUserIdAndFolderId(Long userId, Long folderId);

    /**
     * Gets file count.
     *
     * @return the file count
     */
    Long getFileCount();

    /**
     * Gets file name by file id and user id.
     *
     * @param fileId the file id
     * @param userId the user id
     * @return the file name by file id and user id
     */
    String getFileNameByFileIdAndUserId(Long fileId, Long userId);
}
