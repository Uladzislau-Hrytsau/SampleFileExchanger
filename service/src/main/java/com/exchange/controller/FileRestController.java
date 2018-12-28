package com.exchange.controller;

import com.exchange.dao.File;
import com.exchange.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for user.
 * Created by Hrytsau Uladzislau on 12.12.18.
 */
@CrossOrigin
@RestController
public class FileRestController {

    @Autowired
    private FileService fileService;

    @GetMapping("/files")
    @ResponseStatus(value = HttpStatus.OK)
    public List<File> getAllFiles() {
        return fileService.getAllFiles();
    }

    @GetMapping("/file/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public File getFileById(@PathVariable(value = "id") Long id) {
        return fileService.getFileById(id);
    }

    @GetMapping("/files/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<File> getAllFilesByUserId(@PathVariable(value = "userId") Long userId) {
        return fileService.getAllFilesByUserId(userId);
    }

    @PostMapping("/file")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long addFile(@RequestBody File file) {
        return fileService.addFile(file);
    }

    @PutMapping("/file")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateFile(@RequestBody File file) {
        fileService.updateFile(file);
    }

    @DeleteMapping("/file/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteFile(@PathVariable(value = "id") Long id) {
        fileService.deleteFile(id);
    }

}
