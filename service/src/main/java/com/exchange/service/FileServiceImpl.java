package com.exchange.service;

import com.exchange.dao.CategoryDao;
import com.exchange.dao.File;
import com.exchange.dao.FileDao;
import com.exchange.dao.UserDao;
import com.exchange.dao.file.FileWriter;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * The type File service.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    private UserDao userDao;
    private FileDao fileDao;
    private CategoryDao categoryDao;
    private ServletContext servletContext;
    private final FileWriter fileWriter;

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
     * @param userDao        the user dao
     * @param fileDao        the file dao
     * @param categoryDao    the category dao
     * @param servletContext the servlet context
     * @param fileWriter     the file writer
     */
    @Autowired
    public FileServiceImpl(UserDao userDao, FileDao fileDao, CategoryDao categoryDao, ServletContext servletContext, FileWriter fileWriter) {
        this.userDao = userDao;
        this.fileDao = fileDao;
        this.categoryDao = categoryDao;
        this.servletContext = servletContext;
        this.fileWriter = fileWriter;
    }

    @Override
    public List<File> getAllFiles() {
        return fileDao.getAllFiles();
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
    public Long addFile(File file, MultipartFile multipartFile, String userName) {

        // TODO: validate url, description and category of file
        // TODO: set creation file date
        file.setUserId(userDao.getUserIdByLogin(userName));
        // TODO: encode file name
        String fileName = multipartFile.getOriginalFilename();
        String encodeFileName = UUID.randomUUID().toString();
        String filePath = servletContext.getRealPath("WEB-INF/repo" + java.io.File.separator + encodeFileName);


        // TODO: Last step(saving file)
        fileWriter.saveFile(multipartFile, filePath);
        // TODO: End

        //

//        Long userId = file.getUserId();
//        String url = file.getUrl();
//
//        if (userId == null || userId < 0L) {
//            throw new ValidationException(incorrectId);
//        }
//
//        if (!userDao.checkUserByUserId(userId)) {
//            throw new ValidationException(userDoesNotExist);
//        }
//
//        if (url == null || url.isEmpty() || fileDao.checkFileByUrl(url)) {
//            throw new ValidationException(incorrectUrl);
//        }
//
//        if (file.getDate() == null) {
//            file.setDate(LocalDate.now());
//        }
//
//        if (!categoryDao.checkCategoryById(file.getCategoryId())) {
//            throw new ValidationException(incorrectCategory);
//        }
//
//        return fileDao.addFile(file);
        return 0L;
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
