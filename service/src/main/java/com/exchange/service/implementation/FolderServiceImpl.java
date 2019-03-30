package com.exchange.service.implementation;

import com.exchange.config.security.userdetails.UserDetails;
import com.exchange.dao.FileDao;
import com.exchange.dao.FolderDao;
import com.exchange.dto.folder.FolderStructureDto;
import com.exchange.exception.InternalServerException;
import com.exchange.service.FileWriterService;
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
    private final FileWriterService fileWriterService;
    private final FileDao fileDao;
    private final FolderValidator folderValidator;
    private final CommonService commonService;

    @Value("${folderService.incorrectFolderName}")
    private String incorrectFolderName;
    @Value("${folderService.folderDoesNotExist}")
    private String folderDoesNotExist;
    @Value("${folderService.createError}")
    private String createError;
    @Value("${folderService.deleteError}")
    private String deleteError;
    @Value("${folderService.updateError}")
    private String updateError;

    /**
     * Instantiates a new Folder service.
     *
     * @param folderDao         the folder dao
     * @param fileWriterService the file writer service
     * @param fileDao           the file dao
     * @param folderValidator   the folder validator
     * @param commonService     the common service
     */
    @Autowired
    public FolderServiceImpl(
            FolderDao folderDao,
            FileWriterService fileWriterService,
            FileDao fileDao,
            FolderValidator folderValidator,
            CommonService commonService) {
        this.folderDao = folderDao;
        this.fileWriterService = fileWriterService;
        this.fileDao = fileDao;
        this.folderValidator = folderValidator;
        this.commonService = commonService;
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
        Long userId = commonService.getUserIdByAuthentication(authentication);
        folderValidator.validateFolderByUserId(folderId, userId);
        fileWriterService.deleteFilesByNames(fileDao.getFileNamesByFolderIdAndUserId(folderId, userId));
        if (folderDao.deleteByFolderIdAndUserId(folderId, userId) == 0) {
            throw new InternalServerException(deleteError);
        }
    }

    @Override
    public void updateFolder(FolderStructureDto folderStructureDto, Authentication authentication) {
        folderValidator.validateFolderName(folderStructureDto.getName());
        folderValidator.validateFolderId(folderStructureDto.getId());
        if (folderDao.updateFolderNameByFolderIdAndUserId(folderStructureDto, commonService.getUserIdByAuthentication(authentication)) == 0) {
            throw new InternalServerException(updateError);
        }
    }
}
