package com.exchange.service.implementation;

import com.exchange.config.security.userdetails.UserDetails;
import com.exchange.dao.CategoryDao;
import com.exchange.dao.FileDao;
import com.exchange.dao.FolderDao;
import com.exchange.dto.structure.StructureDto;
import com.exchange.service.StructureService;
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

    /**
     * Instantiates a new Structure service.
     *
     * @param fileDao     the file dao
     * @param folderDao   the folder dao
     * @param categoryDao the category dao
     */
    @Autowired
    public StructureServiceImpl(FileDao fileDao, FolderDao folderDao, CategoryDao categoryDao) {
        this.fileDao = fileDao;
        this.folderDao = folderDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public StructureDto getStructureAndCategoriesByFolderIdAndAuthentication(Long folderId, Authentication authentication) {
        // TODO: validate identifier of folder
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        StructureDto structureDto = new StructureDto();
        structureDto.setFileStructureDtos(fileDao.getFilesByUserIdAndFolderId(userId, folderId));
        structureDto.setFolderStructureDtos(folderDao.getFoldersByUserIdAndParentId(userId, folderId));
        structureDto.setCategoryDtos(categoryDao.getCategoriesByUserId(userId));
        return structureDto;
    }
}
