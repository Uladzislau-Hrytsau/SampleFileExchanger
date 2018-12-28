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

import static com.exchange.controller.JsonConverter.asJsonString;
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

    @Mock
    private FileService fileServiceMock;

    @InjectMocks
    private FileRestController fileRestController;

    private MockMvc mockMvc;

    private final File file = new File(
            1L, 1L, "url1", "description1", LocalDate.now(), 1L
    );
    private final File updatedFile = new File(
            2L, 2L, "url2", "description2", 2L
    );

    private String fileJson;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fileRestController)
                .setControllerAdvice(new RestErrorHandler())
                .build();
    }

    @Test
    public void getAllFilesSuccess_1_MockTest() throws Exception {
        given(fileServiceMock.getAllFiles()).willReturn(Collections.singletonList(file));
        mockMvc.perform(get("/files"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[" + asJsonString(file) + "]"));
    }

    @Test
    public void getFileByIdSuccess_1_MockTest() throws Exception {
        given(fileServiceMock.getFileById(anyLong())).willReturn(file);
        mockMvc.perform(get("/file/{id}", anyLong()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(file)));
    }

    @Test
    public void getFileByIdUnSuccess_1_MockTest() throws Exception {
        given(fileServiceMock.getFileById(anyLong())).willThrow(ValidationException.class);
        mockMvc.perform(get("/file/{id}", anyLong()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void getAllFilesByUserIdSuccess_1_MockTest() throws Exception {
        given(fileServiceMock.getAllFilesByUserId(anyLong())).willReturn(Collections.singletonList(file));
        mockMvc.perform(get("/files/{userId}", anyLong()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[" + asJsonString(file) + "]"));
    }

    @Test
    public void getAllFilesByUserIdUnSuccess_1_MockTest() throws Exception {
        given(fileServiceMock.getAllFilesByUserId(anyLong())).willThrow(ValidationException.class);
        mockMvc.perform(get("/files/{userId}", anyLong()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addFileSuccess_1_MockTest() throws Exception {
        fileJson = asJsonString(updatedFile);
        given(fileServiceMock.addFile(any(File.class))).willReturn(anyLong());
        mockMvc.perform(post("/file")
                .contentType(MediaType.APPLICATION_JSON)
                .content(fileJson))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void addFileUnSuccess_1_MockTest() throws Exception {
        fileJson = asJsonString(updatedFile);
        given(fileServiceMock.addFile(any(File.class))).willThrow(ValidationException.class);
        mockMvc.perform(post("/file")
                .contentType(MediaType.APPLICATION_JSON)
                .content(fileJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateFileSuccess_1_MockTest() throws Exception {
        fileJson = asJsonString(updatedFile);
        doNothing().when(fileServiceMock).updateFile(any(File.class));
        mockMvc.perform(put("/file")
                .contentType(MediaType.APPLICATION_JSON)
                .content(fileJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateFileUnSuccess_1_MockTest() throws Exception {
        fileJson = asJsonString(updatedFile);
        doThrow(ValidationException.class).when(fileServiceMock).updateFile(any(File.class));
        mockMvc.perform(put("/file")
                .contentType(MediaType.APPLICATION_JSON)
                .content(fileJson))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateFileUnSuccess_2_MockTest() throws Exception {
        fileJson = asJsonString(updatedFile);
        doThrow(InternalServerException.class).when(fileServiceMock).updateFile(any(File.class));
        mockMvc.perform(put("/file")
                .contentType(MediaType.APPLICATION_JSON)
                .content(fileJson))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void deleteFileSuccess_1_MockTest() throws Exception {
        doNothing().when(fileServiceMock).deleteFile(anyLong());
        mockMvc.perform(delete("/file/{id}", anyLong()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFileUnSuccess_1_MockTest() throws Exception {
        doThrow(ValidationException.class).when(fileServiceMock).deleteFile(anyLong());
        mockMvc.perform(delete("/file/{id}", anyLong()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteFileUnSuccess_2_MockTest() throws Exception {
        doThrow(InternalServerException.class).when(fileServiceMock).deleteFile(anyLong());
        mockMvc.perform(delete("/file/{id}", anyLong()))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

}
