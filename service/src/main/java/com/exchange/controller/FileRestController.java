package com.exchange.controller;

import com.exchange.dto.StructureDto;
import com.exchange.dto.file.FileUpdatingDto;
import com.exchange.service.FileService;
import com.exchange.wrapper.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * The type File rest controller.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FileRestController {

    private FileService fileService;

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
     * Gets all files.
     *
     * @param page           the page
     * @param size           the size
     * @param authentication the authentication
     * @return the all files
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/files", params = {"page", "size"})
    @ResponseStatus(value = HttpStatus.OK)
    public Response getFilesByPageAndSize(
            @RequestParam(value = "page", required = false, defaultValue = "null") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "null") Integer size,
            Authentication authentication) {
        return fileService.getFilesAndCountByPageAndSize(page, size, authentication);
    }

    /**
     * Gets all folders by folder id.
     *
     * @param folderId       the folder id
     * @param authentication the authentication
     * @return the all folders by folder id
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/structures", params = {"folderId"})
    @ResponseStatus(value = HttpStatus.OK)
    public StructureDto getFoldersByFolderId(
            @RequestParam(value = "folderId") Long folderId,
            Authentication authentication) {
        return fileService.getFilesAndFoldersByFolderId(authentication, folderId);
    }

    /**
     * Add file long.
     *
     * @param jsonFile       the json file
     * @param multipartFile  the multipart file
     * @param authentication the authentication
     * @return the long
     * @throws IOException the io exception
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/files", consumes = "multipart/form-data")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long addFile(
            @RequestParam("file") String jsonFile,
            @RequestParam("multipartFile") MultipartFile multipartFile,
            Authentication authentication) throws IOException {
        return fileService.addFile(jsonFile, multipartFile, authentication);
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
     * Delete file.
     *
     * @param id the id
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/files", params = {"id"})
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteFile(@RequestParam(value = "id") Long id) {
        fileService.deleteFile(id);
    }

}
