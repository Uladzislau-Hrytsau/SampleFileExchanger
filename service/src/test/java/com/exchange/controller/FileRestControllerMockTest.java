package com.exchange.controller;

import com.exchange.controller.handler.RestErrorHandler;
import com.exchange.dao.File;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.exchange.service.FileService;
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
import java.util.Collections;

import static com.exchange.controller.converter.JsonConverter.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class FileRestControllerMockTest {
    private static final String FILES_URL_QUERY = "/files";
    private static final String FILE_ID_URL_QUERY = "/file/{id}";
    private static final String FILES_USER_ID_URL_QUERY = "/files/{userId}";
    private static final String FILE_URL_QUERY = "/file";
    private Long FILE_ID = 1L;
    private Long USER_ID = 1L;
    private String FILE_URL = "url";
    private String FILE_DESCRIPTION = "description";
    private Long FILE_CATEGORY_ID = 1L;
    @Mock
    private FileService fileServiceMock;
    @InjectMocks
    private FileRestController fileRestController;
    private MockMvc mockMvc;
    private File file = new File(
            FILE_ID,
            USER_ID,
            FILE_URL,
            FILE_DESCRIPTION,
            FILE_CATEGORY_ID);

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fileRestController)
                .setControllerAdvice(new RestErrorHandler())
                .alwaysDo(print())
                .build();
    }

    @Test
    public void getAllFilesSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 1));
        given(fileServiceMock.getAllFiles()).willReturn(Collections.singletonList(file));
        mockMvc.perform(get(FILES_URL_QUERY)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(Collections.singletonList(file))));
    }

    @Test
    public void getFileByIdSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 2));
        given(fileServiceMock.getFileById(anyLong())).willReturn(file);
        mockMvc.perform(get(FILE_ID_URL_QUERY, anyLong())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(file)));
    }

    @Test
    public void getFileByIdUnSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 3));
        given(fileServiceMock.getFileById(anyLong())).willThrow(ValidationException.class);
        mockMvc.perform(get(FILE_ID_URL_QUERY, anyLong())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllFilesByUserIdSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 4));
        given(fileServiceMock.getAllFilesByUserId(anyLong())).willReturn(Collections.singletonList(file));
        mockMvc.perform(get(FILES_USER_ID_URL_QUERY, anyLong())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(Collections.singletonList(file))));
    }

    @Test
    public void getAllFilesByUserIdUnSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 5));
        given(fileServiceMock.getAllFilesByUserId(anyLong())).willThrow(ValidationException.class);
        mockMvc.perform(get(FILES_USER_ID_URL_QUERY, anyLong())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addFileSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 6));
        given(fileServiceMock.addFile(any(File.class))).willReturn(anyLong());
        mockMvc.perform(post(FILE_URL_QUERY)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(file)))
                .andExpect(status().isCreated());
    }

    @Test
    public void addFileUnSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 7));
        given(fileServiceMock.addFile(any(File.class))).willThrow(ValidationException.class);
        mockMvc.perform(post(FILE_URL_QUERY)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(file)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateFileSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 8));
        doNothing().when(fileServiceMock).updateFile(any(File.class));
        mockMvc.perform(put(FILE_URL_QUERY)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(file)))
                .andExpect(status().isOk());
    }

    @Test
    public void updateFileUnSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 9));
        doThrow(ValidationException.class).when(fileServiceMock).updateFile(any(File.class));
        mockMvc.perform(put(FILE_URL_QUERY)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(file)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateFileUnSuccess_2_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 10));
        doThrow(InternalServerException.class).when(fileServiceMock).updateFile(any(File.class));
        mockMvc.perform(put(FILE_URL_QUERY)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(file)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void deleteFileSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 11));
        doNothing().when(fileServiceMock).deleteFile(anyLong());
        mockMvc.perform(delete(FILE_ID_URL_QUERY, anyLong())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFileUnSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 12));
        doThrow(ValidationException.class).when(fileServiceMock).deleteFile(anyLong());
        mockMvc.perform(delete(FILE_ID_URL_QUERY, anyLong())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteFileUnSuccess_2_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 13));
        doThrow(InternalServerException.class).when(fileServiceMock).deleteFile(anyLong());
        mockMvc.perform(delete(FILE_ID_URL_QUERY, anyLong())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isInternalServerError());
    }
}
