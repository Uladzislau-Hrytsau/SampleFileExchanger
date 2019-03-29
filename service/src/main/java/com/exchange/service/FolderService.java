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

    /**
     * Delete by folder id and authentication.
     *
     * @param folderId       the folder id
     * @param authentication the authentication
     */
    void deleteByFolderIdAndAuthentication(Long folderId, Authentication authentication);

    /**
     * Update folder.
     *
     * @param folderStructureDto the folder structure dto
     * @param authentication     the authentication
     */
    void updateFolder(FolderStructureDto folderStructureDto, Authentication authentication);
}
