package com.exchange.service.implementation;

import com.exchange.config.security.userdetails.UserDetails;
import com.exchange.dao.FolderDao;
import com.exchange.dto.folder.FolderStructureDto;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.exchange.service.FolderService;
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

    @Value("${folderService.incorrectFolderName}")
    private String incorrectFolderName;
    @Value("${folderService.folderDoesNotExist}")
    private String folderDoesNotExist;
    @Value("${folderService.createError}")
    private String createError;

    /**
     * Instantiates a new Folder service.
     *
     * @param folderDao the folder dao
     */
    @Autowired
    public FolderServiceImpl(FolderDao folderDao) {
        this.folderDao = folderDao;
    }

    @Override
    public void addFolder(FolderStructureDto folderStructureDto, Authentication authentication) {
        String name = folderStructureDto.getName();
        System.out.println(name);
        System.out.println(name);
        System.out.println(name);
        System.out.println(name);
        System.out.println(name);
        System.out.println(name);
        System.out.println(name);
        System.out.println(name);
        System.out.println(name);
        System.out.println(name);
        System.out.println(name);
        if (name == null || name.isEmpty()) {
            throw new ValidationException(incorrectFolderName);
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        if (!folderDao.existsParentIdByUserId(folderStructureDto.getId(), userId)) {
            throw new ValidationException(folderDoesNotExist);
        }
        if (folderDao.addFolder(folderStructureDto, userId) == 0) {
            throw new InternalServerException(createError);
        }
    }
}
