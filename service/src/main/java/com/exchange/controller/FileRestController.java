package com.exchange.controller;

import com.exchange.dao.File;
import com.exchange.dto.StructureDto;
import com.exchange.service.FileService;
import com.exchange.wrapper.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
     * @param page the page
     * @param size the size
     * @return the all files
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/files", params = {"page", "size"})
    @ResponseStatus(value = HttpStatus.OK)
    public Response getAllFiles(
            @RequestParam(value = "page", required = false, defaultValue = "null") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "null") Integer size) {
        return fileService.getFilesAndCountByPageAndSize(page, size);
    }

    /**
     * Gets all folders by folder id.
     *
     * @param authentication the authentication
     * @param folderId       the folder id
     * @return the all folders by folder id
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/structure/{folderId}")
    @ResponseStatus(value = HttpStatus.OK)
    public StructureDto getAllFoldersByFolderId(
            Authentication authentication,
            @PathVariable(value = "folderId") Long folderId) {
        return fileService.getAllFilesAndFoldersByFolderId(authentication, folderId);
    }

    /**
     * Gets file by id.
     *
     * @param id the id
     * @return the file by id
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/file/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public File getFileById(@PathVariable(value = "id") Long id) {
        return fileService.getFileById(id);
    }

    /**
     * Gets all files by user id.
     *
     * @param userId the user id
     * @return the all files by user id
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/files/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<File> getAllFilesByUserId(@PathVariable(value = "userId") Long userId) {
        return fileService.getAllFilesByUserId(userId);
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
    @PostMapping(value = "/file", consumes = "multipart/form-data")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long addFile(@RequestParam("file") String jsonFile, @RequestParam("multipartFile") MultipartFile multipartFile, Authentication authentication) throws IOException {
        return fileService.addFile(jsonFile, multipartFile, authentication);
    }

    /**
     * Update file.
     *
     * @param file the file
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/file")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateFile(@RequestBody File file) {
        fileService.updateFile(file);
    }

    /**
     * Delete file.
     *
     * @param id the id
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/file/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteFile(@PathVariable(value = "id") Long id) {
        fileService.deleteFile(id);
    }

}
