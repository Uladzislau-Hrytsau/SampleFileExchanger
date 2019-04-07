package com.exchange.dao.file;

import com.exchange.dao.FileWriter;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The type File writer.
 */
@Repository
public class FileWriterImpl implements FileWriter {

    @Override
    public void saveFile(final MultipartFile multipartFile, final String filePath) {
        File file = new File(filePath);
        FileOutputStream fileOutputStream = null;
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean deleteFileByPath(final String path) {
        return new File(path).delete();
    }

    @Override
    public File getFileByName(String fileName) {
        return new File(fileName);
    }
}
