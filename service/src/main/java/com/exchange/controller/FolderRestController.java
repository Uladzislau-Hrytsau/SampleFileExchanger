package com.exchange.controller;

import com.exchange.dto.folder.FolderStructureDto;
import com.exchange.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * The type Folder rest controller.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/folders")
public class FolderRestController {

    private final FolderService folderService;

    /**
     * Instantiates a new Folder rest controller.
     *
     * @param folderService the folder service
     */
    @Autowired
    public FolderRestController(final FolderService folderService) {
        this.folderService = folderService;
    }

    /**
     * Add folder.
     *
     * @param folderStructureDto the folder structure dto
     * @param authentication     the authentication
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addFolder(
            @RequestBody final FolderStructureDto folderStructureDto,
            final Authentication authentication) {
        folderService.addFolder(folderStructureDto, authentication);
    }

    /**
     * Update folder name.
     *
     * @param folderStructureDto the folder structure dto
     * @param authentication     the authentication
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void updateFolderName(
            @RequestBody final FolderStructureDto folderStructureDto,
            final Authentication authentication) {
        folderService.updateFolder(folderStructureDto, authentication);
    }

    /**
     * Delete folder.
     *
     * @param folderId       the folder id
     * @param authentication the authentication
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping(params = {"folderId"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteFolder(
            @RequestParam("folderId") final Long folderId,
            final Authentication authentication) {
        folderService.deleteByFolderIdAndAuthentication(folderId, authentication);
    }
}


