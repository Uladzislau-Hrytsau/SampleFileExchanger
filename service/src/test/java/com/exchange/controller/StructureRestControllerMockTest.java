package com.exchange.controller;

import com.exchange.controller.handler.RestErrorHandler;
import com.exchange.dto.category.CategoryDto;
import com.exchange.dto.file.FileStructureDto;
import com.exchange.dto.folder.FolderStructureDto;
import com.exchange.dto.structure.StructureDto;
import com.exchange.exception.ValidationException;
import com.exchange.service.StructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static com.exchange.controller.FolderRestControllerMockTest.TIMES_ONE;
import static com.exchange.controller.converter.JsonConverter.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Structure rest controller mock test.
 */
@RunWith(MockitoJUnitRunner.class)
public class StructureRestControllerMockTest {

    private static final List<FileStructureDto> CORRECT_FILE_STRUCTURE_DTOS = Arrays.asList(
            new FileStructureDto(1L, 1L, "name"),
            new FileStructureDto(1L, 1L, "name")
    );
    private static final List<FolderStructureDto> CORRECT_FOLDER_STRUCTURE_DTOS = Arrays.asList(
            new FolderStructureDto(1L, "name"),
            new FolderStructureDto(1L, "name")
    );
    private static final List<CategoryDto> CORRECT_CATEGORY_DTOS = Arrays.asList(
            new CategoryDto(1L, "name"),
            new CategoryDto(1L, "name")
    );
    private static final StructureDto CORRECT_STRUCTURE_DTO = new StructureDto(
            CORRECT_FILE_STRUCTURE_DTOS,
            CORRECT_FOLDER_STRUCTURE_DTOS,
            CORRECT_CATEGORY_DTOS);
    private static final String FOLDER_ID_PARAM = "folderId";
    private static final Long CORRECT_FOLDER_ID = 1L;

    @Mock
    private StructureService structureServiceMock;
    @Mock
    private Authentication authenticationMock;
    @InjectMocks
    private StructureRestController structureRestController;
    private MockMvc mockMvc;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(structureRestController)
                .setControllerAdvice(new RestErrorHandler())
                .alwaysDo(print())
                .build();
    }

    /**
     * Gets structure by folder id and categories correct folder id and authentication correct structure dto returned.
     *
     * @throws Exception the exception
     */
    @Test
    public void getStructureByFolderIdAndCategories_correctFolderIdAndAuthentication_correctStructureDtoReturned() throws Exception {
        given(structureServiceMock.getStructureAndCategoriesByFolderIdAndAuthentication(any(Long.class), any(Authentication.class)))
                .willReturn(CORRECT_STRUCTURE_DTO);
        mockMvc.perform(get("/structures")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(FOLDER_ID_PARAM, String.valueOf(CORRECT_FOLDER_ID))
                .principal(authenticationMock))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(CORRECT_STRUCTURE_DTO)));
        verify(structureServiceMock, times(TIMES_ONE)).getStructureAndCategoriesByFolderIdAndAuthentication(CORRECT_FOLDER_ID, authenticationMock);
        verifyNoMoreInteractions(structureServiceMock);
    }

    /**
     * Gets structure by folder id and categories correct folder id and authentication validation exception.
     *
     * @throws Exception the exception
     */
    @Test
    public void getStructureByFolderIdAndCategories_correctFolderIdAndAuthentication_validationException() throws Exception {
        given(structureServiceMock.getStructureAndCategoriesByFolderIdAndAuthentication(any(Long.class), any(Authentication.class)))
                .willThrow(ValidationException.class);
        mockMvc.perform(get("/structures")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(FOLDER_ID_PARAM, String.valueOf(CORRECT_FOLDER_ID))
                .principal(authenticationMock))
                .andExpect(status().isBadRequest());
        verify(structureServiceMock, times(TIMES_ONE)).getStructureAndCategoriesByFolderIdAndAuthentication(CORRECT_FOLDER_ID, authenticationMock);
        verifyNoMoreInteractions(structureServiceMock);
    }

    @Test
    public void getStructureByFolderIdAndCategories_nonFolderIdAndAuthentication_validationException() throws Exception {
        mockMvc.perform(get("/structures")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .principal(authenticationMock))
                .andExpect(status().isBadRequest());
        verify(structureServiceMock, never()).getStructureAndCategoriesByFolderIdAndAuthentication(CORRECT_FOLDER_ID, authenticationMock);
        verifyNoMoreInteractions(structureServiceMock);
    }

}
