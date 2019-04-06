package com.exchange.service.validation;

import com.exchange.dao.FolderDao;
import com.exchange.exception.ValidationException;
import com.exchange.service.validation.CommonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type Folder validator.
 */
@Component
public class FolderValidator {

    private final CommonValidator commonValidator;
    private final FolderDao folderDao;
    @Value("${folderService.incorrectFolderId}")
    private String incorrectFolderId;
    @Value("${folderService.folderDoesNotExist}")
    private String folderDoesNotExist;
    @Value("${folderService.incorrectFolderName}")
    private String incorrectFolderName;

    /**
     * Instantiates a new Folder validator.
     *
     * @param commonValidator the common validator
     * @param folderDao       the folder dao
     */
    @Autowired
    public FolderValidator(
            final CommonValidator commonValidator,
            final FolderDao folderDao) {
        this.commonValidator = commonValidator;
        this.folderDao = folderDao;
    }

    /**
     * Validate folder by user id.
     *
     * @param folderId the folder id
     * @param userId   the user id
     */
    public void validateFolderByUserId(
            final Long folderId,
            final Long userId) {
        this.validateFolderId(folderId);
        this.existsByUserId(folderId, userId);
    }

    /**
     * Validate folder id.
     *
     * @param folderId the folder id
     */
    public void validateFolderId(final Long folderId) {
        if (!commonValidator.isValidIdentifier(folderId)) {
            throw new ValidationException(incorrectFolderId);
        }
    }

    /**
     * Exists by user id.
     *
     * @param folderId the folder id
     * @param userId   the user id
     */
    public void existsByUserId(
            final Long folderId,
            final Long userId) {
        if (!folderDao.existsParentIdByUserId(folderId, userId)) {
            throw new ValidationException(folderDoesNotExist);
        }
    }

    /**
     * Validate folder name.
     *
     * @param name the name
     */
    public void validateFolderName(final String name) {
        if (name == null || name.isEmpty()) {
            throw new ValidationException(incorrectFolderName);
        }
    }
}
