package com.exchange.service.implementation;

import com.exchange.config.security.userdetails.UserDetails;
import com.exchange.dao.File;
import com.exchange.dao.FileDao;
import com.exchange.dao.Pagination;
import com.exchange.dto.file.FileDto;
import com.exchange.dto.file.FileUpdatingDto;
import com.exchange.exception.InternalServerException;
import com.exchange.service.CategoryService;
import com.exchange.service.FileService;
import com.exchange.service.FileWriterService;
import com.exchange.service.validation.file.FileValidator;
import com.exchange.wrapper.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.UUID;

/**
 * The type File service.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    private final FileDao fileDao;
    private final FileValidator fileValidator;
    private final CategoryService categoryService;
    private final FileWriterService fileWriterService;

    @Value("${fileService.incorrectId}")
    private String incorrectId;
    @Value("${fileService.incorrectDescription}")
    private String izncorrectDescription;
    @Value("${fileService.incorrectRealName}")
    private String incorrectFileName;
    @Value("${fileService.updateError}")
    private String updateError;
    @Value("${fileService.deleteError}")
    private String deleteError;

    /**
     * Instantiates a new File service.
     *
     * @param fileDao           the file dao
     * @param fileValidator     the file validator
     * @param categoryService   the category service
     * @param fileWriterService the file writer service
     */
    @Autowired
    public FileServiceImpl(
            FileDao fileDao,
            FileValidator fileValidator,
            CategoryService categoryService,
            FileWriterService fileWriterService) {
        this.fileDao = fileDao;
        this.fileValidator = fileValidator;
        this.categoryService = categoryService;
        this.fileWriterService = fileWriterService;
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
    public Long addFile(FileDto fileDto, MultipartFile multipartFile, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        fileValidator.validateSize(multipartFile);
        fileValidator.validateDescription(fileDto.getDescription());
        String encodeName = UUID.randomUUID().toString();
        fileDto.setUserId(userDetails.getUserId());
        fileDto.setRealName(multipartFile.getOriginalFilename());
        fileDto.setDate(LocalDate.now());
        fileDto.setEncodeName(encodeName);
        Long fileId = fileDao.addFile(fileDto);
        categoryService.addFileCategories(fileDto.getCategories(), fileId, userDetails.getUserId());
        fileWriterService.saveFile(multipartFile, encodeName);
        return fileId;
    }

    @Override
    public void updateFile(FileUpdatingDto fileUpdatingDto) {
        fileValidator.validateFileId(fileUpdatingDto.getId());
        fileValidator.validateDescription(fileUpdatingDto.getDescription());
        fileValidator.validateRealName(fileUpdatingDto.getRealName());
        if (fileUpdatingDto.getDate() == null) {
            fileUpdatingDto.setDate(LocalDate.now());
        }
        if (fileDao.updateFile(fileUpdatingDto) == 0) {
            throw new InternalServerException(updateError);
        }
    }

    @Override
    public java.io.File downloadFileByFileIdAndAuthentication(Long fileId, Authentication authentication) throws MalformedURLException, FileNotFoundException {
        // TODO: get encode name by file id
        String fileName = "10628559-1489-43d1-bbc4-a391ac1fc4e3";
        return fileWriterService.getFileByName(fileName);
    }

    @Override
    public void
    deleteFile(Long id, Authentication authentication) {
        // TODO: remove real files from all child directory
        fileValidator.validateFileId(id);
        Long userId = this.getUserIdByAuthentication(authentication);
        if (fileDao.deleteFile(id, userId) == 0) {
            throw new InternalServerException(deleteError);
        }
    }

    @Override
    public Long getFileCount() {
        return fileDao.getFileCount();
    }

    // TODO: to do common method
    private Long getUserIdByAuthentication(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }
}
