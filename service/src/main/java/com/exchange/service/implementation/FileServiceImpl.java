package com.exchange.service.implementation;

import com.exchange.config.security.userdetails.UserDetails;
import com.exchange.dao.CategoryDao;
import com.exchange.dao.File;
import com.exchange.dao.FileDao;
import com.exchange.dao.Pagination;
import com.exchange.dao.file.FileWriter;
import com.exchange.dto.file.FileCategoryDto;
import com.exchange.dto.file.FileDto;
import com.exchange.dto.file.FileUpdatingDto;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.exchange.service.FileService;
import com.exchange.wrapper.Response;
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
import java.util.Set;
import java.util.UUID;

/**
 * The type File service.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    private final FileDao fileDao;
    private final CategoryDao categoryDao;
    private final ServletContext servletContext;
    private final FileWriter fileWriter;
    private final ObjectMapper objectMapper;

    @Value("${fileService.incorrectId}")
    private String incorrectId;
    @Value("${fileService.incorrectDescription}")
    private String incorrectDescription;
    @Value("${fileService.incorrectRealName}")
    private String incorrectFileName;
    @Value("${fileService.updateError}")
    private String updateError;
    @Value("${fileService.deleteError}")
    private String deleteError;

    /**
     * Instantiates a new File service.
     *
     * @param fileDao        the file dao
     * @param categoryDao    the category dao
     * @param servletContext the servlet context
     * @param fileWriter     the file writer
     * @param objectMapper   the object mapper
     */
    @Autowired
    public FileServiceImpl(FileDao fileDao, CategoryDao categoryDao, ServletContext servletContext, FileWriter fileWriter, ObjectMapper objectMapper) {
        this.fileDao = fileDao;
        this.categoryDao = categoryDao;
        this.servletContext = servletContext;
        this.fileWriter = fileWriter;
        this.objectMapper = objectMapper;
    }

    @Override
    public Response getFilesAndCountByPageAndSize(Integer page, Integer size, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // TODO: validate page and size
        Integer offset = size * --page;
        Response<File> response = new Response<>();
        response.setData(fileDao.getFilesByLimitAndOffset(size, offset));
        response.setPagination(new Pagination(this.getFileCount()));
        return response;
    }

    @Override
    public Long addFile(String jsonFile, MultipartFile multipartFile, Authentication authentication) throws IOException {
        // TODO: clean-up
        FileDto fileDto = objectMapper.readValue(jsonFile, FileDto.class);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String description = fileDto.getDescription();
        if (description == null || description.isEmpty()) {
            throw new ValidationException();
        }
        if (!categoryDao.existsByCategories(fileDto.getCategories(), userDetails.getUserId())) {
            throw new ValidationException();
        }
        fileDto.setUserId(userDetails.getUserId());
        fileDto.setRealName(multipartFile.getOriginalFilename());
        String encodeName = UUID.randomUUID().toString();
        fileDto.setEncodeName(encodeName);
        fileDto.setDate(LocalDate.now());
        String filePath = servletContext.getRealPath("WEB-INF/repo" + java.io.File.separator + encodeName);
        fileWriter.saveFile(multipartFile, filePath);
        Long fileId = fileDao.addFile(fileDto);
        Set<FileCategoryDto> fileCategoryDtos = new HashSet<>(fileDto.getCategories().size());
        Set<Long> fileCategories = fileDto.getCategories();
        fileCategories.forEach(item -> {
            fileCategoryDtos.add(new FileCategoryDto(item, fileId));
        });
        categoryDao.addFileCategories(fileCategoryDtos);
        return fileId;
    }

    @Override
    public void updateFile(FileUpdatingDto fileUpdatingDto) {
        Long id = fileUpdatingDto.getId();
        String description = fileUpdatingDto.getDescription();
        String realName = fileUpdatingDto.getRealName();
        if (id == null || id < 0L) {
            throw new ValidationException(incorrectId);
        }
        if (description == null || description.isEmpty()) {
            throw new ValidationException(incorrectDescription);
        }
        if (realName == null || realName.isEmpty()) {
            throw new ValidationException(incorrectFileName);
        }
        if (fileUpdatingDto.getDate() == null) {
            fileUpdatingDto.setDate(LocalDate.now());
        }
        if (fileDao.updateFile(fileUpdatingDto) == 0) {
            throw new InternalServerException(updateError);
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

    @Override
    public Long getFileCount() {
        return fileDao.getFileCount();
    }
}
