package com.exchange.service.validation.folder;

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

    /**
     * Instantiates a new Folder validator.
     *
     * @param commonValidator the common validator
     * @param folderDao       the folder dao
     */
    @Autowired
    public FolderValidator(
            CommonValidator commonValidator,
            FolderDao folderDao) {
        this.commonValidator = commonValidator;
        this.folderDao = folderDao;
    }

    /**
     * Validate folder by user id.
     *
     * @param folderId the folder id
     * @param userId   the user id
     */
    public void validateFolderByUserId(Long folderId, Long userId) {
        this.validateFolderId(folderId);
        this.existsByUserId(folderId, userId);
    }

    private void validateFolderId(Long folderId) {
        if (!commonValidator.isValidIdentifier(folderId)) {
            throw new ValidationException(incorrectFolderId);
        }
    }

    private void existsByUserId(Long folderId, Long userId) {
        if (!folderDao.existsParentIdByUserId(folderId, userId)) {
            throw new ValidationException(folderDoesNotExist);
        }
    }
}
