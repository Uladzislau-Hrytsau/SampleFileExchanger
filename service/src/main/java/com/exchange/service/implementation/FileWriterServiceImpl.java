package com.exchange.service.implementation;

import com.exchange.dao.FileWriter;
import com.exchange.exception.FileNotDeletedException;
import com.exchange.service.FileWriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The type File writer service.
 */
@Service
@Transactional
public class FileWriterServiceImpl implements FileWriterService {

    private static final String REPOSITORY_PATH = "WEB-INF/repo";

    private final FileWriter fileWriter;
    private final ServletContext servletContext;

    @Value("${fileService.deleteError}")
    private String deleteError;

    /**
     * Instantiates a new File writer service.
     *
     * @param fileWriter     the file writer
     * @param servletContext the servlet context
     */
    @Autowired
    public FileWriterServiceImpl(
            final FileWriter fileWriter,
            final ServletContext servletContext) {
        this.fileWriter = fileWriter;
        this.servletContext = servletContext;
    }

    @Override
    public void saveFile(
            final MultipartFile multipartFile,
            final String encodeName) throws IOException {
        fileWriter.saveFile(multipartFile, this.getFilePath(encodeName));
    }

    @Override
    public File getFileByName(final String fileName) {
        return fileWriter.getFileByName(this.getFilePath(fileName));
    }

    @Override
    public void deleteFileByName(final String fileName) {
        if (!fileWriter.deleteFileByPath(this.getFilePath(fileName))) {
            throw new FileNotDeletedException(deleteError);
        }
    }

    @Override
    public void deleteFilesByNames(final List<String> fileNames) {
        fileNames.forEach(this::deleteFileByName);
    }

    public String getFilePath(final String encodeName) {
        return servletContext.getRealPath(REPOSITORY_PATH + java.io.File.separator + encodeName);
    }
}
