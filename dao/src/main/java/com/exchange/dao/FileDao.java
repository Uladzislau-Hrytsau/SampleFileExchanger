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
     * Update file boolean.
     *
     * @param fileUpdatingDto the file updating dto
     * @return the boolean
     */
    Boolean updateFile(FileUpdatingDto fileUpdatingDto);

    /**
     * Delete file boolean.
     *
     * @param fileId the file id
     * @return the boolean
     */
    Boolean deleteFile(Long fileId);

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
     * Gets file name by file id.
     *
     * @param fileId the file id
     * @return the file name by file id
     */
    String getFileNameByFileId(Long fileId);

    /**
     * Gets file names by folder id and user id.
     *
     * @param folderId the folder id
     * @param userId   the user id
     * @return the file names by folder id and user id
     */
    List<String> getFileNamesByFolderIdAndUserId(Long folderId, Long userId);

    /**
     * Gets file information by file id and user id.
     *
     * @param fileId the file id
     * @param userId the user id
     * @return the file information by file id and user id
     */
    FileUpdatingDto getFileInformationByFileIdAndUserId(Long fileId, Long userId);

    /**
     * Gets file names by user id.
     *
     * @param userId the user id
     * @return the file names by user id
     */
    List<String> getFileNamesByUserId(Long userId);
}
