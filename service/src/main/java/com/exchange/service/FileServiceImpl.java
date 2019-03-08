package com.exchange.service;

import com.exchange.config.security.userdetails.UserDetails;
import com.exchange.dao.CategoryDao;
import com.exchange.dao.File;
import com.exchange.dao.FileDao;
import com.exchange.dao.UserDao;
import com.exchange.dao.file.FileWriter;
import com.exchange.dto.FileCategoryDto;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private FileWriter fileWriter;
    private ObjectMapper objectMapper;

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
     * @param objectMapper   the object mapper
     */
    @Autowired
    public FileServiceImpl(UserDao userDao, FileDao fileDao, CategoryDao categoryDao, ServletContext servletContext, FileWriter fileWriter, ObjectMapper objectMapper) {
        this.userDao = userDao;
        this.fileDao = fileDao;
        this.categoryDao = categoryDao;
        this.servletContext = servletContext;
        this.fileWriter = fileWriter;
        this.objectMapper = objectMapper;
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
    public Long addFile(String jsonFile, MultipartFile multipartFile, Authentication authentication) throws IOException {
        // TODO: checked exception?
        // TODO: clean-up
        File file = objectMapper.readValue(jsonFile, File.class);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String description = file.getDescription();
        if (description == null || description.isEmpty()) {
            throw new ValidationException();
        }
        if (!categoryDao.existsByCategories(file.getCategories(), userDetails.getUserId())) {
            throw new ValidationException();
        }
        file.setUserId(userDetails.getUserId());
        file.setRealName(multipartFile.getOriginalFilename());
        String encodeName;
        do {
            encodeName = UUID.randomUUID().toString();
        } while (fileDao.existsByEncodeName(encodeName));
        file.setEncodeName(encodeName);
        file.setDate(LocalDate.now());
        String filePath = servletContext.getRealPath("WEB-INF/repo" + java.io.File.separator + encodeName);
        fileWriter.saveFile(multipartFile, filePath);
        Long fileId = fileDao.addFile(file);

        Set<FileCategoryDto> fileCategoryDtos = new HashSet<>(file.getCategories().size());
        Set<Long> fileCategories = file.getCategories();

        fileCategories.forEach(item -> {
            fileCategoryDtos.add(new FileCategoryDto(item, fileId));
        });
        categoryDao.addFileCategories(fileCategoryDtos);
        return fileId;
    }

    @Override
    public void updateFile(File file) {

        String description = file.getDescription();
//        Long category = file.getCategoryId();

        if (description == null || description.isEmpty()) {
            throw new ValidationException(incorrectDescription);
        }

//        if (category == null || category < 0L || !categoryDao.checkCategoryById(category)) {
//            throw new ValidationException(incorrectCategory);
//        }

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
