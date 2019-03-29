package com.exchange.controller;

import com.exchange.dto.file.FileDto;
import com.exchange.dto.file.FileUpdatingDto;
import com.exchange.service.FileService;
import com.exchange.wrapper.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The type File rest controller.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FileRestController {

    private final FileService fileService;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Instantiates a new File rest controller.
     *
     * @param fileService                the file service
     * @param namedParameterJdbcTemplate the named parameter jdbc template
     */
    @Autowired
    public FileRestController(FileService fileService, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.fileService = fileService;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

    }

    /**
     * Gets files by page and size.
     *
     * @param page           the page
     * @param size           the size
     * @param authentication the authentication
     * @return the files by page and size
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/files", params = {"page", "size"})
    @ResponseStatus(value = HttpStatus.OK)
    public Response getFilesByPageAndSize(
            @RequestParam(value = "page", required = false, defaultValue = "null") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "null") Integer size,
            Authentication authentication) {
        return fileService.getFilesAndCountByPageAndSize(page, size, authentication);
    }

    /**
     * Add file long.
     *
     * @param multipartFile  the multipart file
     * @param fileDto        the file dto
     * @param authentication the authentication
     * @return the long
     * @throws IOException the io exception
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/files", consumes = "multipart/form-data")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long addFile(
            @RequestPart("multipartFile") MultipartFile multipartFile,
            @RequestPart("metaData") FileDto fileDto,
            Authentication authentication) throws IOException {
        return fileService.addFile(fileDto, multipartFile, authentication);
    }

    /**
     * Update file.
     *
     * @param fileUpdatingDto the file updating dto
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/files")
    @ResponseStatus(value = HttpStatus.OK)
    public void updateFile(@RequestBody FileUpdatingDto fileUpdatingDto) {
        fileService.updateFile(fileUpdatingDto);
    }

    /**
     * Download file.
     *
     * @param fileId         the file id
     * @param fileName       the file name
     * @param authentication the authentication
     * @param response       the response
     * @throws IOException the io exception
     */
    @GetMapping(value = "/files", params = {"fileId", "fileName"})
    @ResponseStatus(value = HttpStatus.OK)
    public void downloadFile(
            @RequestParam("fileId") Long fileId,
            @RequestParam("fileName") String fileName,
            Authentication authentication,
            HttpServletResponse response) throws IOException {
        fileService.downloadFileByFileIdAndAuthentication(fileId, fileName, authentication, response);
    }

    /**
     * Delete file.
     *
     * @param id             the id
     * @param authentication the authentication
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/files", params = {"id"})
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteFile(
            @RequestParam(value = "id") Long id,
            Authentication authentication) {
        fileService.deleteFile(id, authentication);
    }

    /**
     * Check list.
     *
     * @param number the number
     * @return the list
     */
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/check", params = {"number"})
    @ResponseStatus(value = HttpStatus.OK)
    public List<String> check(
            @RequestParam(value = "number") Long number) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("number", number);
        return namedParameterJdbcTemplate.queryForList(" select encode_name from\n" +
                " file inner join\n" +
                "(select  id \n" +
                "from    (select * from folder\n" +
                "         order by parent_id, id) products_sorted,\n" +
                "        (select @pv := :number) initialisation\n" +
                " where find_in_set(parent_id, @pv)\n" +
                " and length(@pv := concat(@pv, ',', id))\n" +
                " or id = :number) as tab\n" +
                " on file.folder_id = tab.id", parameterSource, String.class);
    }

}
