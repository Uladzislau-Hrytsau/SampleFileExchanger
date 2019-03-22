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
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FolderRestController {

    private final FolderService folderService;

    /**
     * Instantiates a new Folder rest controller.
     *
     * @param folderService the folder service
     */
    @Autowired
    public FolderRestController(FolderService folderService) {
        this.folderService = folderService;
    }

    /**
     * Add folder.
     *
     * @param folderStructureDto the folder structure dto
     * @param authentication     the authentication
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping("/folders")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFolder(
            @RequestBody FolderStructureDto folderStructureDto,
            Authentication authentication) {
        folderService.addFolder(folderStructureDto, authentication);
    }

    /**
     * Delete folder.
     *
     * @param folderId       the folder id
     * @param authentication the authentication
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/folders", params = {"folderId"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteFolder(
            @RequestParam("folderId") Long folderId,
            Authentication authentication) {
        folderService.deleteByFolderIdAndAuthentication(folderId, authentication);
    }

}
