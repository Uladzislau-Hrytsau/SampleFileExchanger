package com.exchange.controller;

import com.exchange.dto.file.FileDto;
import com.exchange.dto.file.FileUpdatingDto;
import com.exchange.service.FileService;
import com.exchange.wrapper.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type File rest controller.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FileRestController {

    private final FileService fileService;

    /**
     * Instantiates a new File rest controller.
     *
     * @param fileService the file service
     */
    @Autowired
    public FileRestController(FileService fileService) {
        this.fileService = fileService;

    }

    /**
     * Gets files by page and size.
     *
     * @param page the page
     * @param size the size
     * @return the files by page and size
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/files", params = {"page", "size"})
    @ResponseStatus(value = HttpStatus.OK)
    public Response getFilesByPageAndSize(
            @RequestParam(value = "page", required = false, defaultValue = "null") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "null") Integer size) {
        return fileService.getFilesAndCountByPageAndSize(page, size);
    }

    /**
     * Add file long.
     *
     * @param multipartFile  the multipart file
     * @param fileDto        the file dto
     * @param authentication the authentication
     * @return the long
     * @throws IOException the io exception
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/files", consumes = "multipart/form-data")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long addFile(
            @RequestPart("multipartFile") MultipartFile multipartFile,
            @RequestPart("metaData") FileDto fileDto,
            Authentication authentication) throws IOException {
        return fileService.addFile(fileDto, multipartFile, authentication);
    }

    /**
     * Update file.
     *
     * @param fileUpdatingDto the file updating dto
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/files")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateFile(@RequestBody FileUpdatingDto fileUpdatingDto) {
        fileService.updateFile(fileUpdatingDto);
    }

    /**
     * Download file.
     *
     * @param fileId   the file id
     * @param fileName the file name
     * @param response the response
     * @throws IOException the io exception
     */
    @GetMapping(value = "/files", params = {"fileId", "fileName"})
    @ResponseStatus(value = HttpStatus.OK)
    public void downloadFile(
            @RequestParam("fileId") Long fileId,
            @RequestParam("fileName") String fileName,
            HttpServletResponse response) throws IOException {
        fileService.downloadFileByFileId(fileId, fileName, response);
    }

    /**
     * Delete file.
     *
     * @param id the id
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/files", params = {"id"})
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteFile(
            @RequestParam(value = "id") Long id) {
        fileService.deleteFile(id);
    }

    /**
     * Gets file information by file id.
     *
     * @param fileId         the file id
     * @param authentication the authentication
     * @return the file information by file id
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/files", params = {"fileId"})
    @ResponseStatus(value = HttpStatus.OK)
    public FileUpdatingDto getFileInformationByFileId(
            @RequestParam(value = "fileId") Long fileId,
            Authentication authentication) {
        return fileService.getFileInformationByFileIdAndAuthentication(fileId, authentication);
    }

}
