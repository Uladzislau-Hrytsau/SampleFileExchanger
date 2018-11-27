package com.exchange.test.dao;

import org.springframework.dao.DataAccessException;

import java.util.Date;
import java.util.List;

/**
 * FileDao interface.
 */
public interface FileDao {

    /**
     *
     * @param userId
     * @param category
     * @return
     * @throws DataAccessException
     */
    List<File> getAllFilesByUserIdAndCategory(Long userId, String category) throws DataAccessException;

    /**
     *
     * @param userId
     * @param date
     * @return
     * @throws DataAccessException
     */
    List<File> getAllFilesByUserIdAndDate(Long userId, Date date) throws DataAccessException;

    /**
     *
     * @param userId
     * @return
     * @throws DataAccessException
     */
    List<File> getAllFilesByUserId(Long userId) throws DataAccessException;

    /**
     *
     * @return
     * @throws DataAccessException
     */
    List<File> getAllFiles() throws DataAccessException;

    /**
     *
     * @param id
     * @return
     * @throws DataAccessException
     */
    File getFileById(Long id) throws DataAccessException;

    /**
     *
     * @param file
     * @return
     * @throws DataAccessException
     */
    Long addFile(File file) throws DataAccessException;

    /**
     *
     * @param file
     * @return
     * @throws DataAccessException
     */
    int updateFile(File file) throws DataAccessException;

    /**
     *
     * @param id
     * @return
     * @throws DataAccessException
     */
    int deleteFile(Long id) throws DataAccessException;

}
