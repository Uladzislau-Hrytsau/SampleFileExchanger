package com.exchange.service.validation;

import com.exchange.exception.ValidationException;
import com.exchange.service.validation.CommonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * The type File validator.
 */
@Component
public class FileValidator {

    private static final Long MAX_FILE_SIZE = Long.MAX_VALUE;
    private final CommonValidator commonValidator;
    @Value("${fileService.fileNotPresented}")
    private String fileNotPresented;
    @Value("${fileService.fileIsLargerThanMaxUploadSize}")
    private String fileIsLargerThanMaxUploadSize;
    @Value("${fileService.incorrectDescription}")
    private String incorrectDescription;
    @Value("${fileService.incorrectId}")
    private String incorrectId;
    @Value("${fileService.incorrectRealName}")
    private String incorrectFileName;

    /**
     * Instantiates a new File validator.
     *
     * @param commonValidator the common validator
     */
    @Autowired
    public FileValidator(final CommonValidator commonValidator) {
        this.commonValidator = commonValidator;
    }

    /**
     * Validate file id.
     *
     * @param fileId the file id
     */
    public void validateFileId(final Long fileId) {
        if (!commonValidator.isValidIdentifier(fileId)) {
            throw new ValidationException(incorrectId);
        }
    }

    /**
     * Validate description.
     *
     * @param description the description
     */
    public void validateDescription(final String description) {
        if (!commonValidator.isValidString(description)) {
            throw new ValidationException(incorrectDescription);
        }
    }

    /**
     * Validate name.
     *
     * @param realName the real name
     */
    public void validateName(final String realName) {
        if (!commonValidator.isValidString(realName)) {
            throw new ValidationException(incorrectFileName);
        }
    }

}
