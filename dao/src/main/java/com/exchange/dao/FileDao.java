package com.exchange.dao;

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
    List<File> getAllFilesByUserId(Long userId) throws DataAccessException;

    /**
     * Gets all files.
     *
     * @return the all files
     * @throws DataAccessException the data access exception
     */
    List<File> getAllFiles() throws DataAccessException;

    /**
     * Gets file by id.
     *
     * @param id the id
     * @return the file by id
     * @throws DataAccessException the data access exception
     */
    File getFileById(Long id) throws DataAccessException;

    /**
     * Add file long.
     *
     * @param file the file
     * @return the long
     * @throws DataAccessException the data access exception
     */
    Long addFile(File file) throws DataAccessException;

    /**
     * Update file int.
     *
     * @param file the file
     * @return the int
     * @throws DataAccessException the data access exception
     */
    int updateFile(File file) throws DataAccessException;

    /**
     * Delete file int.
     *
     * @param id the id
     * @return the int
     * @throws DataAccessException the data access exception
     */
    int deleteFile(Long id) throws DataAccessException;

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

}
