package com.exchange.service.implementation;

import com.exchange.dao.File;
import com.exchange.dao.FileDao;
import com.exchange.dao.Pagination;
import com.exchange.dto.file.FileDto;
import com.exchange.dto.file.FileUpdatingDto;
import com.exchange.exception.InternalServerException;
import com.exchange.service.CategoryService;
import com.exchange.service.FileService;
import com.exchange.service.FileWriterService;
import com.exchange.service.validation.CommonValidator;
import com.exchange.service.validation.FileValidator;
import com.exchange.wrapper.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.List;
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
    private final CommonService commonService;
    private final CommonValidator commonValidator;

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
     * @param fileDao           the file dao
     * @param fileValidator     the file validator
     * @param categoryService   the category service
     * @param fileWriterService the file writer service
     * @param commonService     the common service
     * @param commonValidator   the common validator
     */
    @Autowired
    public FileServiceImpl(
            final FileDao fileDao,
            final FileValidator fileValidator,
            final CategoryService categoryService,
            final FileWriterService fileWriterService,
            final CommonService commonService,
            final CommonValidator commonValidator) {
        this.fileDao = fileDao;
        this.fileValidator = fileValidator;
        this.categoryService = categoryService;
        this.fileWriterService = fileWriterService;
        this.commonService = commonService;
        this.commonValidator = commonValidator;
    }

    @Override
    public Response getFilesAndCountByPageAndSize(
            final Integer page,
            final Integer size) {
        commonValidator.validatePageAndSize(page, size);
        Response<File> response = new Response<>();
        response.setData(
                fileDao.getFilesByLimitAndOffset(size, commonService.getOffsetBySizeAndPage(size, page)));
        response.setPagination(new Pagination(this.getFileCount()));
        return response;
    }

    @Override
    public Long addFile(
            final FileDto fileDto,
            final MultipartFile multipartFile,
            final Authentication authentication) throws IOException {
        Long userId = commonService.getUserIdByAuthentication(authentication);
        fileValidator.validateDescription(fileDto.getDescription());
        String encodeName = UUID.randomUUID().toString();
        fileDto.setUserId(userId);
        fileDto.setRealName(multipartFile.getOriginalFilename());
        fileDto.setDate(LocalDate.now());
        fileDto.setEncodeName(encodeName);
        Long fileId = fileDao.addFile(fileDto);
        categoryService.addFileCategories(fileDto.getCategories(), fileId, userId);
        fileWriterService.saveFile(multipartFile, encodeName);
        return fileId;
    }

    @Override
    public void updateFile(
            final FileUpdatingDto fileUpdatingDto) {
        fileValidator.validateFileId(fileUpdatingDto.getId());
        fileValidator.validateDescription(fileUpdatingDto.getDescription());
        fileValidator.validateName(fileUpdatingDto.getRealName());
        if (fileUpdatingDto.getDate() == null) {
            fileUpdatingDto.setDate(LocalDate.now());
        }
        if (!fileDao.updateFile(fileUpdatingDto)) {
            throw new InternalServerException(updateError);
        }
    }

    @Override
    public void downloadFileByFileId(
            final Long fileId,
            final String fileName,
            final HttpServletResponse response) throws IOException {
        fileValidator.validateName(fileName);
        fileValidator.validateFileId(fileId);
        String encodedFileName = fileDao.getFileNameByFileId(fileId);
        java.io.File file = fileWriterService.getFileByName(encodedFileName);
        this.buildFileDownloadResponse(response, fileName, (int) file.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    @Override
    public void deleteFile(final Long fileId) {
        fileValidator.validateFileId(fileId);
        fileWriterService.deleteFileByName(fileDao.getFileNameByFileId(fileId));
        if (!fileDao.deleteFile(fileId)) {
            throw new InternalServerException(deleteError);
        }
    }

    @Override
    public Long getFileCount() {
        return fileDao.getFileCount();
    }

    @Override
    public FileUpdatingDto getFileInformationByFileIdAndAuthentication(final Long fileId, final Authentication authentication) {
        fileValidator.validateFileId(fileId);
        return fileDao.getFileInformationByFileIdAndUserId(fileId, commonService.getUserIdByAuthentication(authentication));
    }

    @Override
    public List<String> getFileNamesByUserId(final Long userId) {
        return fileDao.getFileNamesByUserId(userId);
    }

    /**
     * Build file download response.
     *
     * @param response the response
     * @param fileName the file name
     * @param fileSize the file size
     */
    public void buildFileDownloadResponse(final HttpServletResponse response, final String fileName, final Integer fileSize) {
        response.setContentType(this.getFileTypeByFileName(fileName));
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
        response.setContentLength(fileSize);
    }

    /**
     * Gets file type by file name.
     *
     * @param fileName the file name
     * @return the file type by file name
     */
    public String getFileTypeByFileName(final String fileName) {
        String mimeType = URLConnection.guessContentTypeFromName(fileName);
        if (mimeType == null) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return mimeType;
    }

}
