package com.exchange.controller;

import com.exchange.dto.structure.StructureDto;
import com.exchange.service.StructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * The type Structure rest controller.
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StructureRestController {

    private final StructureService structureService;

    /**
     * Instantiates a new Structure rest controller.
     *
     * @param structureService the structure service
     */
    @Autowired
    public StructureRestController(final StructureService structureService) {
        this.structureService = structureService;
    }

    /**
     * Gets structure by folder id and categories.
     *
     * @param folderId       the folder id
     * @param authentication the authentication
     * @return the structure by folder id and categories
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/structures", params = {"folderId"})
    @ResponseStatus(value = HttpStatus.OK)
    public StructureDto getStructureByFolderIdAndCategories(
            @RequestParam(value = "folderId") final Long folderId,
            final Authentication authentication) {
        return structureService.getStructureAndCategoriesByFolderIdAndAuthentication(folderId, authentication);
    }

}
