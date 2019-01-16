package com.exchange.controller;

import com.exchange.controller.converter.JsonConverter;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type File rest controller mock test.
 */
@RunWith(MockitoJUnitRunner.class)
public class FileRestControllerMockTest {

    @Mock
    private FileService fileServiceMock;

    @InjectMocks
    private FileRestController fileRestController;

    private MockMvc mockMvc;

    private File file = new File(
            1L,
            1L,
            "url1",
            "description1",
            1L
    );

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fileRestController)
                .setControllerAdvice(new RestErrorHandler())
                .build();
    }

    /**
     * Gets all files success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void getAllFilesSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 1));
        given(fileServiceMock.getAllFiles()).willReturn(Collections.singletonList(file));
        mockMvc.perform(get("/files"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JsonConverter.asJsonString(Collections.singletonList(file))));
    }

    /**
     * Gets file by id success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void getFileByIdSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 2));
        given(fileServiceMock.getFileById(anyLong())).willReturn(file);
        mockMvc.perform(get("/file/{id}", anyLong()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JsonConverter.asJsonString(file)));
    }

    /**
     * Gets file by id un success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void getFileByIdUnSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 3));
        given(fileServiceMock.getFileById(anyLong())).willThrow(ValidationException.class);
        mockMvc.perform(get("/file/{id}", anyLong()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    /**
     * Gets all files by user id success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void getAllFilesByUserIdSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 4));
        given(fileServiceMock.getAllFilesByUserId(anyLong())).willReturn(Collections.singletonList(file));
        mockMvc.perform(get("/files/{userId}", anyLong()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(JsonConverter.asJsonString(Collections.singletonList(file))));
    }

    /**
     * Gets all files by user id un success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void getAllFilesByUserIdUnSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 5));
        given(fileServiceMock.getAllFilesByUserId(anyLong())).willThrow(ValidationException.class);
        mockMvc.perform(get("/files/{userId}", anyLong()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Add file success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void addFileSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 6));
        given(fileServiceMock.addFile(any(File.class))).willReturn(anyLong());
        mockMvc.perform(post("/file")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonConverter.asJsonString(file)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    /**
     * Add file un success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void addFileUnSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 7));
        given(fileServiceMock.addFile(any(File.class))).willThrow(ValidationException.class);
        mockMvc.perform(post("/file")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonConverter.asJsonString(file)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Update file success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateFileSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 8));
        doNothing().when(fileServiceMock).updateFile(any(File.class));
        mockMvc.perform(put("/file")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonConverter.asJsonString(file)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Update file un success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateFileUnSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 9));
        doThrow(ValidationException.class).when(fileServiceMock).updateFile(any(File.class));
        mockMvc.perform(put("/file")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonConverter.asJsonString(file)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Update file un success 2 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void updateFileUnSuccess_2_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 10));
        doThrow(InternalServerException.class).when(fileServiceMock).updateFile(any(File.class));
        mockMvc.perform(put("/file")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(JsonConverter.asJsonString(file)))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    /**
     * Delete file success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteFileSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 11));
        doNothing().when(fileServiceMock).deleteFile(anyLong());
        mockMvc.perform(delete("/file/{id}", anyLong()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Delete file un success 1 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteFileUnSuccess_1_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 12));
        doThrow(ValidationException.class).when(fileServiceMock).deleteFile(anyLong());
        mockMvc.perform(delete("/file/{id}", anyLong()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /**
     * Delete file un success 2 mock test.
     *
     * @throws Exception the exception
     */
    @Test
    public void deleteFileUnSuccess_2_MockTest() throws Exception {
        file.setDate(LocalDate.of(2019, 1, 13));
        doThrow(InternalServerException.class).when(fileServiceMock).deleteFile(anyLong());
        mockMvc.perform(delete("/file/{id}", anyLong()))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

}
