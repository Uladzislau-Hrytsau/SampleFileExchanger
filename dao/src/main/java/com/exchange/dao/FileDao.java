package com.exchange.dao;

import org.springframework.dao.DataAccessException;

import java.util.List;

/**
 * FileDao interface.
 * Created by Uladzislau Hrytsau on 27.11.18.
 */
public interface FileDao {

    /**
     * Get all files list by user-id.
     *
     * @param userId user identifier.
     * @return file. all files by user id
     * @throws DataAccessException the data access exception
     */
    List<File> getAllFilesByUserId(Long userId) throws DataAccessException;

    /**
     * Get all files list.
     *
     * @return file. all files
     * @throws DataAccessException the data access exception
     */
    List<File> getAllFiles() throws DataAccessException;

    /**
     * Get file by id.
     *
     * @param id user identifier.
     * @return file. file by id
     * @throws DataAccessException the data access exception
     */
    File getFileById(Long id) throws DataAccessException;

    /**
     * Create new file.
     *
     * @param file user file
     * @return new file id.
     * @throws DataAccessException the data access exception
     */
    Long addFile(File file) throws DataAccessException;

    /**
     * Update file.
     *
     * @param file user file.
     * @return new user id.
     * @throws DataAccessException the data access exception
     */
    int updateFile(File file) throws DataAccessException;

    /**
     * Delete file.
     *
     * @param id file identifier.
     * @return int int
     * @throws DataAccessException the data access exception
     */
    int deleteFile(Long id) throws DataAccessException;

    /**
     * Check file by identifier.
     *
     * @param id file identifier.
     * @return boolean boolean
     */
    boolean checkFileById(Long id);

    /**
     * Check file by user identifier.
     *
     * @param userId user identifier.
     * @return boolean boolean
     */
    boolean checkFileByUserId(Long userId);

    /**
     * Check file by file url.
     *
     * @param url file url.
     * @return boolean boolean
     */
    boolean checkFileByUrl(String url);

}
