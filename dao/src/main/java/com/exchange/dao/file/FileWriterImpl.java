package com.exchange.dao.file;

import com.exchange.dao.FileWriter;
import com.exchange.dao.exception.FileNotCreatedException;
import com.exchange.dao.exception.FileNotExistException;
import com.exchange.dao.exception.FileNotWrittenException;
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
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            if (file.createNewFile()) {
                throw new FileNotCreatedException(errorCreatingFile);
            }
            fileOutputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new FileNotWrittenException(errorSavingFile);
        }
    }

    @Override
    public Boolean deleteFileByPath(final String path) {
        return new File(path).delete();
    }

    @Override
    public File getFileByName(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotExistException(errorGettingFile);
        }
        return file;
    }
}
