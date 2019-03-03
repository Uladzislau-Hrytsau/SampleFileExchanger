package com.exchange.controller;

import com.exchange.dao.File;
import com.exchange.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * @return the all files
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/files")
    @ResponseStatus(value = HttpStatus.OK)
    public List<File> getAllFiles() {
        return fileService.getAllFiles();
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
     * @param file           the file
     * @param multipartFile  the multipart file
     * @param authentication the authentication
     * @return the long
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/file")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long addFile(@RequestBody File file, @RequestParam("file") MultipartFile multipartFile, Authentication authentication) {
        return fileService.addFile(file, multipartFile, authentication);
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
