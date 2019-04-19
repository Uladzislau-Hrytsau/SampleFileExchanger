package com.exchange.dao.file;

import com.exchange.dao.FileWriter;
import com.exchange.dao.exception.FileNotCreatedException;
import com.exchange.dao.exception.FileNotExistException;
import com.exchange.dao.exception.FileNotWrittenException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The type File writer.
 */
@Repository
public class FileWriterImpl implements FileWriter {

    private static final Logger LOGGER = LogManager.getLogger();


    @Value("${fileWriter.errorCreatingFile}")
    private String errorCreatingFile;
    @Value("${fileWriter.errorSavingFile}")
    private String errorSavingFile;
    @Value("${fileWriter.errorGettingFile}")
    private String errorGettingFile;
    @Value("${fileWriter.errorDeletingFile}")
    private String errorDeletingFile;

    @Override
    public void saveFile(final MultipartFile multipartFile, final String filePath) {
        File file = new File(filePath);
        LOGGER.info(filePath + " from " + this.getClass().getName());
        LOGGER.info("filePath.equals(file.getAbsolutePath()) is " + filePath.equals(file.getAbsolutePath()));
        LOGGER.info("file.exists()" + file.exists() + file.getAbsolutePath());
        LOGGER.info("file.canRead()" + file.canRead() + file.getAbsolutePath());
        LOGGER.info("file.canWrite()" + file.canWrite() + file.getAbsolutePath());
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            LOGGER.info(file.exists());
            if (file.createNewFile()) {

                LOGGER.info("file.exists()" + file.exists() + file.getAbsolutePath());
                LOGGER.info("file.canRead()" + file.canRead() + file.getAbsolutePath());
                LOGGER.info("file.canWrite()" + file.canWrite() + file.getAbsolutePath());

                LOGGER.trace("saveFile() from " + this.getClass().getName() + " with message " + errorCreatingFile);
                LOGGER.info("file.exists() " + file.exists());
                throw new FileNotCreatedException(errorCreatingFile);
            }
            LOGGER.info("file.exists() " + file.exists());
            LOGGER.info("file is created in saveFile from " + this.getClass().getName());
            LOGGER.info("fileOutputStream.toString() is " + fileOutputStream.toString());
            fileOutputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            LOGGER.trace("saveFile() from " + this.getClass().getName() + " with message " + errorSavingFile);
            LOGGER.trace(e);
            throw new FileNotWrittenException(errorSavingFile);
        }
    }

    @Override
    public Boolean deleteFileByPath(final String path) {
        return new File(path).delete();
    }

    @Override
    public File getFileByName(final String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotExistException(errorGettingFile);
        }
        return file;
    }
}
