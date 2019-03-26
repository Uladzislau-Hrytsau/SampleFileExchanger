package com.exchange.service.implementation;

import com.exchange.dao.file.FileWriter;
import com.exchange.service.FileWriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * The type File writer service.
 */
@Service
@Transactional
public class FileWriterServiceImpl implements FileWriterService {

    // TODO: take it from the config
    private static final String REPOSITORY_PATH = "WEB-INF/repo";

    private final FileWriter fileWriter;
    private final ServletContext servletContext;

    /**
     * Instantiates a new File writer service.
     *
     * @param fileWriter     the file writer
     * @param servletContext the servlet context
     */
    @Autowired
    public FileWriterServiceImpl(
            FileWriter fileWriter,
            ServletContext servletContext) {
        this.fileWriter = fileWriter;
        this.servletContext = servletContext;
    }

    @Override
    public void saveFile(MultipartFile multipartFile, String encodeName) {
        fileWriter.saveFile(multipartFile, this.getFilePath(encodeName));
    }

    @Override
    public File getFileByName(String fileName) throws FileNotFoundException {
        File file = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        file = new File(this.getFilePath(fileName));
        if (!file.exists()) {
            System.out.println("file does not exist");
            System.out.println("file does not exist");
            System.out.println("file does not exist");
            System.out.println("file does not exist");
        }
        return file;

    }

    private String getFilePath(String encodeName) {
        return servletContext.getRealPath(REPOSITORY_PATH + java.io.File.separator + encodeName);
    }
}
