package com.exchange.service.validation;

import com.exchange.exception.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * The type File validator.
 */
@Component
public class FileValidator {

    private static final Long MAX_FILE_SIZE = Long.MAX_VALUE;
    @Value("${fileService.fileNotPresented}")
    private String fileNotPresented;
    @Value("${fileService.fileIsLargerThanMaxUploadSize}")
    private String fileIsLargerThanMaxUploadSize;

    /**
     * Validate size.
     *
     * @param multipartFile the multipart file
     */
    public void validateSize(MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new ValidationException(fileNotPresented);
        }
        if (multipartFile.getSize() > MAX_FILE_SIZE) {
            throw new ValidationException(fileIsLargerThanMaxUploadSize);
        }
    }

    /**
     * Validate description.
     *
     * @param description the description
     */
    public void validateDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new ValidationException();
        }
    }

}
