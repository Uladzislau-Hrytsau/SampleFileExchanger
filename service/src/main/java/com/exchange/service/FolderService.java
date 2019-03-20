package com.exchange.service;

import com.exchange.dto.folder.FolderStructureDto;
import org.springframework.security.core.Authentication;

/**
 * The interface Folder service.
 */
public interface FolderService {

    /**
     * Add folder.
     *
     * @param folderStructureDto the folder structure dto
     * @param authentication     the authentication
     */
    void addFolder(FolderStructureDto folderStructureDto, Authentication authentication);

}
