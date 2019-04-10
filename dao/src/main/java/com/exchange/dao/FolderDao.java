package com.exchange.dao;

import com.exchange.dto.folder.FolderStructureDto;

import java.util.List;

/**
 * The interface Folder dao.
 */
public interface FolderDao {

    /**
     * Gets folders by user id and parent id.
     *
     * @param userId   the user id
     * @param parentId the parent id
     * @return the folders by user id and parent id
     */
    List<FolderStructureDto> getFoldersByUserIdAndParentId(Long userId, Long parentId);

    /**
     * Add folder long.
     *
     * @param folderStructureDto the folder structure dto
     * @param userId             the user id
     * @return the long
     */
    Long addFolder(FolderStructureDto folderStructureDto, Long userId);

    /**
     * Exists parent id by user id boolean.
     *
     * @param parentId the parent id
     * @param userId   the user id
     * @return the boolean
     */
    Boolean existsParentIdByUserId(Long parentId, Long userId);

    /**
     * Delete by folder id and user id boolean.
     *
     * @param folderId the folder id
     * @param userId   the user id
     * @return the boolean
     */
    Boolean deleteByFolderIdAndUserId(Long folderId, Long userId);

    /**
     * Update folder name by folder id and user id boolean.
     *
     * @param folderStructureDto the folder structure dto
     * @param userId             the user id
     * @return the boolean
     */
    Boolean updateFolderNameByFolderIdAndUserId(FolderStructureDto folderStructureDto, Long userId);
}
