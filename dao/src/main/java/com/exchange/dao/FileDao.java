package com.exchange.dao;

import com.exchange.dto.FileStructureDto;
import org.springframework.dao.DataAccessException;

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
     * @throws DataAccessException the data access exception
     */
    List<File> getAllFilesByUserId(Long userId);

    /**
     * Gets all files.
     *
     * @return the all files
     * @throws DataAccessException the data access exception
     */
    List<File> getAllFiles();

    /**
     * Gets file by id.
     *
     * @param id the id
     * @return the file by id
     * @throws DataAccessException the data access exception
     */
    File getFileById(Long id);

    /**
     * Add file long.
     *
     * @param file the file
     * @return the long
     * @throws DataAccessException the data access exception
     */
    Long addFile(File file);

    /**
     * Update file int.
     *
     * @param file the file
     * @return the int
     * @throws DataAccessException the data access exception
     */
    int updateFile(File file);

    /**
     * Delete file int.
     *
     * @param id the id
     * @return the int
     * @throws DataAccessException the data access exception
     */
    int deleteFile(Long id);

    /**
     * Check file by id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean checkFileById(Long id);

    /**
     * Check file by user id boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    boolean checkFileByUserId(Long userId);

    /**
     * Check file by url boolean.
     *
     * @param url the url
     * @return the boolean
     */
    boolean checkFileByUrl(String url);

    /**
     * Exists by encode name boolean.
     *
     * @param encodeName the encode name
     * @return the boolean
     */
    boolean existsByEncodeName(String encodeName);

    /**
     * Gets all files by user id and folder id.
     *
     * @param userId   the user id
     * @param folderId the folder id
     * @return the all files by user id and folder id
     */
    List<FileStructureDto> getAllFilesByUserIdAndFolderId(Long userId, Long folderId);
}
