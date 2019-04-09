package com.exchange.controller;

import com.exchange.controller.handler.RestErrorHandler;
import com.exchange.dao.File;
import com.exchange.dao.Pagination;
import com.exchange.exception.ValidationException;
import com.exchange.service.FileService;
import com.exchange.wrapper.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.exchange.controller.converter.JsonConverter.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class FileRestControllerMockTest {

    public static final int TIMES_ONE = 1;
    private static final String PAGE_PARAM = "page";
    private static final String SIZE_PARAM = "size";
    private static final String USERS_URI = "/files";
    private static final Integer CORRECT_PAGE = 1;
    private static final Integer CORRECT_SIZE = 2;
    private static final Long CORRECT_COUNT = 2L;
    private static final Long CORRECT_ID = 1L;
    private static final File CORRECT_FILE = new File(
            1L, 1L, 1L, "description",
            "realName", "encodeName", LocalDate.of(1000, 10, 10));
    private static final List<File> CORRECT_FILES = Arrays.asList(
            CORRECT_FILE,
            CORRECT_FILE);
    private static final Pagination CORRECT_PAGINATION = new Pagination(CORRECT_COUNT);
    private static final Response CORRECT_RESPONSE = new Response(
            CORRECT_FILES,
            CORRECT_PAGINATION);

    @Mock
    private FileService fileServiceMock;
    @InjectMocks
    private FileRestController fileRestController;
    private MockMvc mockMvc;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fileRestController)
                .setControllerAdvice(new RestErrorHandler())
                .alwaysDo(print())
                .build();
    }

    @Test
    public void getUsersByPageAndSize_correctPageAndSize_correctResponseReturned() throws Exception {
        given(fileServiceMock.getFilesAndCountByPageAndSize(any(Integer.class), any(Integer.class)))
                .willReturn(CORRECT_RESPONSE);
        mockMvc.perform(get(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(PAGE_PARAM, String.valueOf(CORRECT_PAGE))
                .param(SIZE_PARAM, String.valueOf(CORRECT_SIZE)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(CORRECT_RESPONSE)));
        verify(fileServiceMock, times(TIMES_ONE)).getFilesAndCountByPageAndSize(any(), any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    @Test
    public void getUsersByPageAndSize_incorrectPageAndSize_validationException() throws Exception {
        given(fileServiceMock.getFilesAndCountByPageAndSize(any(Integer.class), any(Integer.class)))
                .willThrow(ValidationException.class);
        mockMvc.perform(get(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param(PAGE_PARAM, String.valueOf(CORRECT_PAGE))
                .param(SIZE_PARAM, String.valueOf(CORRECT_SIZE)))
                .andExpect(status().isBadRequest());
        verify(fileServiceMock, times(TIMES_ONE)).getFilesAndCountByPageAndSize(any(), any());
        verifyNoMoreInteractions(fileServiceMock);
    }

    @Test
    public void getUsersByPageAndSize_nonPageAndSize_badRequest() throws Exception {
        mockMvc.perform(get(USERS_URI)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
        verify(fileServiceMock, never()).getFilesAndCountByPageAndSize(any(), any());
        verifyNoMoreInteractions(fileServiceMock);
    }



}
