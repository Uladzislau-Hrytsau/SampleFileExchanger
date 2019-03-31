package com.exchange.dao.file;

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

    @Override
    public void saveFile(final MultipartFile multipartFile, final String filePath) throws IOException {
        File file = new File(filePath);
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
    }

    @Override
    public Boolean deleteFileByPath(final String path) {
        return new File(path).delete();
    }
}
