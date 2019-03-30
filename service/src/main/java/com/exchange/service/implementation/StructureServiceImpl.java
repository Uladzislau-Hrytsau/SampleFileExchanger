package com.exchange.service.implementation;

import com.exchange.dao.CategoryDao;
import com.exchange.dao.FileDao;
import com.exchange.dao.FolderDao;
import com.exchange.dto.structure.StructureDto;
import com.exchange.service.StructureService;
import com.exchange.service.validation.folder.FolderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Structure service.
 */
@Service
@Transactional
public class StructureServiceImpl implements StructureService {

    private final FileDao fileDao;
    private final FolderDao folderDao;
    private final CategoryDao categoryDao;
    private final CommonService commonService;
    private final FolderValidator folderValidator;

    /**
     * Instantiates a new Structure service.
     *
     * @param fileDao         the file dao
     * @param folderDao       the folder dao
     * @param categoryDao     the category dao
     * @param commonService   the common service
     * @param folderValidator the folder validator
     */
    @Autowired
    public StructureServiceImpl(
            FileDao fileDao,
            FolderDao folderDao,
            CategoryDao categoryDao,
            CommonService commonService,
            FolderValidator folderValidator) {
        this.fileDao = fileDao;
        this.folderDao = folderDao;
        this.categoryDao = categoryDao;
        this.commonService = commonService;
        this.folderValidator = folderValidator;
    }

    @Override
    public StructureDto getStructureAndCategoriesByFolderIdAndAuthentication(Long folderId, Authentication authentication) {
        folderValidator.validateFolderId(folderId);
        Long userId = commonService.getUserIdByAuthentication(authentication);
        StructureDto structureDto = new StructureDto();
        structureDto.setFileStructureDtos(fileDao.getFilesByUserIdAndFolderId(userId, folderId));
        structureDto.setFolderStructureDtos(folderDao.getFoldersByUserIdAndParentId(userId, folderId));
        structureDto.setCategoryDtos(categoryDao.getCategoriesByUserId(userId));
        return structureDto;
    }
}
