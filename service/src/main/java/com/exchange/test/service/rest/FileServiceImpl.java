package com.exchange.test.service.rest;

import com.exchange.test.dao.File;
import com.exchange.test.dao.FileDao;
import com.exchange.test.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDate;
import java.util.List;

/**
 * File Service implementation.
 * Created by Hrytsau Uladzislau on 1.12.18.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    FileDao fileDao;

    public void setFileDao(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    public List<File> getAllFilesByUserIdAndCategory(Long userId, String category) throws NotImplementedException {
        return null;
    }

    @Override
    public List<File> getAllFilesByUserIdAndDate(Long userId, LocalDate date) throws NotImplementedException {
        return null;
    }

    @Override
    public List<File> getAllFilesByUserId(Long userId) throws DataAccessException {
        return fileDao.getAllFilesByUserId(userId);
    }

    @Override
    public List<File> getAllFiles() throws DataAccessException {
        return fileDao.getAllFiles();
    }

    @Override
    public File getFileById(Long id) throws DataAccessException {
        return fileDao.getFileById(id);
    }

    @Override
    public Long addFile(File file) throws DataAccessException {
        return fileDao.addFile(file);
    }

    @Override
    public int updateFile(File file) throws DataAccessException {
        return fileDao.updateFile(file);
    }

    @Override
    public int deleteFile(Long id) throws DataAccessException {
        return fileDao.deleteFile(id);
    }
}
