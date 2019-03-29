package com.exchange.dao.file;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The type File writer.
 */
@Component
public class FileWriterImpl implements FileWriter {

    @Override
    public void saveFile(MultipartFile multipartFile, String filePath) throws IOException {
        File file = new File(filePath);
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
    }

    @Override
    public Boolean deleteFileByPath(String path) {
        return new File(path).delete();
    }
}
