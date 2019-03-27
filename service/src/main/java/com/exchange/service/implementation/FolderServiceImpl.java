package com.exchange.service.implementation;

import com.exchange.config.security.userdetails.UserDetails;
import com.exchange.dao.FolderDao;
import com.exchange.dto.folder.FolderStructureDto;
import com.exchange.exception.InternalServerException;
import com.exchange.service.FolderService;
import com.exchange.service.validation.folder.FolderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Folder service.
 */
@Service
@Transactional
public class FolderServiceImpl implements FolderService {

    private final FolderDao folderDao;
    private final FolderValidator folderValidator;

    @Value("${folderService.incorrectFolderName}")
    private String incorrectFolderName;
    @Value("${folderService.folderDoesNotExist}")
    private String folderDoesNotExist;
    @Value("${folderService.createError}")
    private String createError;
    @Value("${folderService.deleteError}")
    private String deleteError;

    /**
     * Instantiates a new Folder service.
     *
     * @param folderDao       the folder dao
     * @param folderValidator the folder validator
     */
    @Autowired
    public FolderServiceImpl(
            FolderDao folderDao,
            FolderValidator folderValidator) {
        this.folderDao = folderDao;
        this.folderValidator = folderValidator;
    }

    @Override
    public void addFolder(FolderStructureDto folderStructureDto, Authentication authentication) {
        folderValidator.validateFolderName(folderStructureDto.getName());
        if (folderDao.addFolder(folderStructureDto, ((UserDetails) authentication.getPrincipal()).getUserId()) == 0) {
            throw new InternalServerException(createError);
        }
    }

    @Override
    public void deleteByFolderIdAndAuthentication(Long folderId, Authentication authentication) {
        // TODO: remove real files from all child directory
        Long userId = ((UserDetails) authentication.getPrincipal()).getUserId();
        folderValidator.validateFolderByUserId(folderId, userId);
        if (folderDao.deleteByFolderIdAndUserId(folderId, userId) == 0) {
            throw new InternalServerException(deleteError);
        }
    }
}
