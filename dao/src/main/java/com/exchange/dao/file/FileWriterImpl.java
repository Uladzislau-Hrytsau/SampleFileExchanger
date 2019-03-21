package com.exchange.dao.file;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The type File writer.
 */
@Component
public class FileWriterImpl implements FileWriter {

    @Override
    public void saveFile(MultipartFile multipartFile, String filePath) {

        File file = null;
        FileOutputStream fileOutputStream = null;

        try {

            file = new File(filePath);
            file.createNewFile();
            // TODO: try with the transferTo() method
//            multipartFile.transferTo(file);

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
