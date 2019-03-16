package com.exchange.dao;

import com.exchange.dto.folder.FolderStructureDto;

import java.util.List;

/**
 * The interface Folder dao.
 */
public interface FolderDao {

    /**
     * Gets all folders by user id and parent id.
     *
     * @param userId   the user id
     * @param parentId the parent id
     * @return the all folders by user id and parent id
     */
    List<FolderStructureDto> getAllFoldersByUserIdAndParentId(Long userId, Long parentId);

}
