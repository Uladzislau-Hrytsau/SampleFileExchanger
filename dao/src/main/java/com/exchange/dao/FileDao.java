package com.exchange.dao;

import org.springframework.dao.DataAccessException;

import java.util.List;

public interface FileDao {

    List<File> getAllFilesByUserId(Long userId) throws DataAccessException;

    List<File> getAllFiles() throws DataAccessException;

    File getFileById(Long id) throws DataAccessException;

    Long addFile(File file) throws DataAccessException;

    int updateFile(File file) throws DataAccessException;

    int deleteFile(Long id) throws DataAccessException;

    boolean checkFileById(Long id);

    boolean checkFileByUserId(Long userId);

    boolean checkFileByUrl(String url);

}
