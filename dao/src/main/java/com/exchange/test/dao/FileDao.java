package com.exchange.test.dao;

import org.springframework.dao.DataAccessException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * FileDao interface.
 */
public interface FileDao {

    /**
     * Get all files list by user-id and category.
     *
     * @param userId
     * @param userId user identifier.
     * @param category file category.
     * @return file.
     * @throws DataAccessException
     */
    //List<File> getAllFilesByUserIdAndCategory(Long userId, String category) throws DataAccessException;

    /**
     * Get all files list by user-id and date.
     *
     * @param userId user identifier.
     * @param date upload date.
     * @return file.
     * @throws DataAccessException
     */
    //List<File> getAllFilesByUserIdAndDate(Long userId, LocalDate date) throws DataAccessException;

    /**
     * Get all files list by user-id.
     *
     * @param userId user identifier.
     * @return file.
     * @throws DataAccessException
     */
    List<File> getAllFilesByUserId(Long userId) throws DataAccessException;

    /**
     * Get all files list.
     *
     * @return file.
     * @throws DataAccessException
     */
    List<File> getAllFiles() throws DataAccessException;

    /**
     * Get file by id.
     *
     * @param id user identifier.
     * @return file.
     * @throws DataAccessException
     */
    File getFileById(Long id) throws DataAccessException;

    /**
     * Create new file.
     *
     * @param file user file
     * @return new file id.
     * @throws DataAccessException
     */
    Long addFile(File file) throws DataAccessException;

    /**
     * Update file.
     *
     * @param file user file.
     * @return new user id.
     * @throws DataAccessException
     */
    int updateFile(File file) throws DataAccessException;

    /**
     * Delete file.
     *
     * @param id file identifier.
     * @return
     * @throws DataAccessException
     */
    int deleteFile(Long id) throws DataAccessException;

}
