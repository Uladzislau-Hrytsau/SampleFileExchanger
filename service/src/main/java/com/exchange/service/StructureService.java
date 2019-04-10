package com.exchange.service;

import com.exchange.dto.structure.StructureDto;
import org.springframework.security.core.Authentication;

/**
 * The interface Structure service.
 */
public interface StructureService {

    /**
     * Gets structure and categories by folder id and authentication.
     *
     * @param folderId       the folder id
     * @param authentication the authentication
     * @return the structure and categories by folder id and authentication
     */
    StructureDto getStructureAndCategoriesByFolderIdAndAuthentication(Long folderId, Authentication authentication);
}
