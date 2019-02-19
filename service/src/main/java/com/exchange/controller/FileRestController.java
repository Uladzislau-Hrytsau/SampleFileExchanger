package com.exchange.controller;

import com.exchange.dao.File;
import com.exchange.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FileRestController {

    private FileService fileService;

    @Autowired
    public FileRestController(FileService fileService) {
        this.fileService = fileService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/files")
    @ResponseStatus(value = HttpStatus.OK)
    public List<File> getAllFiles() {
        return fileService.getAllFiles();
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/file/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public File getFileById(@PathVariable(value = "id") Long id) {
        return fileService.getFileById(id);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/files/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<File> getAllFilesByUserId(@PathVariable(value = "userId") Long userId) {
        return fileService.getAllFilesByUserId(userId);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('IS_AUTHENTICATED_ANONYMOUSLY')")
    @PostMapping("/file")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long addFile(@RequestBody File file) {
        return fileService.addFile(file);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/file")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateFile(@RequestBody File file) {
        fileService.updateFile(file);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/file/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteFile(@PathVariable(value = "id") Long id) {
        fileService.deleteFile(id);
    }

}
