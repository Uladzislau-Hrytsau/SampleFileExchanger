package com.exchange.service;

import com.exchange.dao.CategoryDao;
import com.exchange.dao.File;
import com.exchange.dao.FileDao;
import com.exchange.dao.UserDao;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * File Service implementation.
 * Created by Hrytsau Uladzislau on 1.12.18.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {


    private UserDao userDao;

    private FileDao fileDao;

    private CategoryDao categoryDao;

    @Value("${fileService.incorrectId}")
    private String incorrectId;
    @Value("${fileService.fileDoesNotExist}")
    private String fileDoesNotExist;
    @Value("${fileService.incorrectUrl}")
    private String incorrectUrl;
    @Value("${userService.userDoesNotExist}")
    private String userDoesNotExist;
    @Value("${fileService.incorrectCategory}")
    private String incorrectCategory;
    @Value("${fileService.incorrectDescription}")
    private String incorrectDescription;
    @Value("${fileService.updateError}")
    private String updateError;
    @Value("${fileService.deleteError}")
    private String deleteError;

    /**
     * Instantiates a new File service.
     *
     * @param userDao     the user dao
     * @param fileDao     the file dao
     * @param categoryDao the category dao
     */
    @Autowired
    public FileServiceImpl(UserDao userDao, FileDao fileDao, CategoryDao categoryDao) {
        this.userDao = userDao;
        this.fileDao = fileDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public List<File> getAllFilesByUserId(Long userId) {
        if (userId == null || userId < 0L)
            throw new ValidationException(incorrectId);
        if (!fileDao.checkFileByUserId(userId))
            throw new ValidationException(fileDoesNotExist);
        return fileDao.getAllFilesByUserId(userId);
    }

    @Override
    public List<File> getAllFiles() {
        return fileDao.getAllFiles();
    }

    @Override
    public File getFileById(Long id) {

        if (id == null || id < 0L) {
            throw new ValidationException(incorrectId);
        }

        if (!fileDao.checkFileById(id)) {
            throw new ValidationException(fileDoesNotExist);
        }
        return fileDao.getFileById(id);
    }

    @Override
    public Long addFile(File file) {

        Long userId = file.getUser_id();
        String url = file.getUrl();

        if (userId == null || userId < 0L) {
            throw new ValidationException(incorrectId);
        }

        if (!userDao.checkUserByUserId(userId)) {
            throw new ValidationException(userDoesNotExist);
        }

        if (url == null || url.isEmpty() || fileDao.checkFileByUrl(url)) {
            throw new ValidationException(incorrectUrl);
        }

        if (file.getDate() == null) {
            file.setDate(LocalDate.now());
        }

        if (!categoryDao.checkCategoryById(file.getCategoryId())) {
            throw new ValidationException(incorrectCategory);
        }

        return fileDao.addFile(file);
    }

    @Override
    public void updateFile(File file) {

        String description = file.getDescription();
        Long category = file.getCategoryId();

        if (description == null || description.isEmpty()) {
            throw new ValidationException(incorrectDescription);
        }

        if (category == null || category < 0L || !categoryDao.checkCategoryById(category)) {
            throw new ValidationException(incorrectCategory);
        }

        if (fileDao.updateFile(file) == 0) {
            throw new InternalServerException(updateError);
        }

        if (file.getDate() == null) {
            file.setDate(LocalDate.now());
        }
    }

    @Override
    public void deleteFile(Long id) {
        if (id == null || id < 0L) {
            throw new ValidationException(incorrectId);
        }
        if (fileDao.deleteFile(id) == 0) {
            throw new InternalServerException(deleteError);
        }
    }
}
