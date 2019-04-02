package com.exchange.controller;

/**
 * The type File rest controller mock test.
 */
//@RunWith(MockitoJUnitRunner.class)
public class FileRestControllerMockTest {
//    private static final String FILES_URL_QUERY = "/files";
//    private static final String FILE_ID_URL_QUERY = "/file/{id}";
//    private static final String FILES_USER_ID_URL_QUERY = "/files/{userId}";
//    private static final String FILE_URL_QUERY = "/file";
//    private Long FILE_ID = 1L;
//    private Long USER_ID = 1L;
//    private String FILE_URL = "url";
//    private String FILE_DESCRIPTION = "description";
//    private Long FILE_CATEGORY_ID = 1L;
//    @Mock
//    private FileService fileServiceMock;
//    @InjectMocks
//    private FileRestController fileRestController;
//    private MockMvc mockMvc;
//    private File file = new File(
//            FILE_ID,
//            USER_ID,
//            FILE_URL,
//            FILE_DESCRIPTION,
//            FILE_CATEGORY_ID);

    /**
     * Sets up.
     */
//    @Before
//    public void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(fileRestController)
//                .setControllerAdvice(new RestErrorHandler())
//                .alwaysDo(print())
//                .build();
//    }

    /**
     * Gets all files success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void getAllFilesSuccess_1_MockTest() throws Exception {
//        file.setDate(LocalDate.of(2019, 1, 1));
//        given(fileServiceMock.getAllFiles()).willReturn(Collections.singletonList(file));
//        mockMvc.perform(get(FILES_URL_QUERY)
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk())
//                .andExpect(content().json(asJsonString(Collections.singletonList(file))));
//    }

    /**
     * Gets file by id success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void getFileByIdSuccess_1_MockTest() throws Exception {
//        file.setDate(LocalDate.of(2019, 1, 2));
//        given(fileServiceMock.getFileById(anyLong())).willReturn(file);
//        mockMvc.perform(get(FILE_ID_URL_QUERY, anyLong())
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk())
//                .andExpect(content().json(asJsonString(file)));
//    }

    /**
     * Gets file by id un success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void getFileByIdUnSuccess_1_MockTest() throws Exception {
//        file.setDate(LocalDate.of(2019, 1, 3));
//        given(fileServiceMock.getFileById(anyLong())).willThrow(ValidationException.class);
//        mockMvc.perform(get(FILE_ID_URL_QUERY, anyLong())
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isBadRequest());
//    }

    /**
     * Gets all files by user id success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void getAllFilesByUserIdSuccess_1_MockTest() throws Exception {
//        file.setDate(LocalDate.of(2019, 1, 4));
//        given(fileServiceMock.getAllFilesByUserId(anyLong())).willReturn(Collections.singletonList(file));
//        mockMvc.perform(get(FILES_USER_ID_URL_QUERY, anyLong())
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk())
//                .andExpect(content().json(asJsonString(Collections.singletonList(file))));
//    }

    /**
     * Gets all files by user id un success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void getAllFilesByUserIdUnSuccess_1_MockTest() throws Exception {
//        file.setDate(LocalDate.of(2019, 1, 5));
//        given(fileServiceMock.getAllFilesByUserId(anyLong())).willThrow(ValidationException.class);
//        mockMvc.perform(get(FILES_USER_ID_URL_QUERY, anyLong())
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isBadRequest());
//    }

    /**
     * Add file success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void addFileSuccess_1_MockTest() throws Exception {
//        file.setDate(LocalDate.of(2019, 1, 6));
//        given(fileServiceMock.addFile(any(File.class))).willReturn(anyLong());
//        mockMvc.perform(post(FILE_URL_QUERY)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(asJsonString(file)))
//                .andExpect(status().isCreated());
//    }

    /**
     * Add file un success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void addFileUnSuccess_1_MockTest() throws Exception {
//        file.setDate(LocalDate.of(2019, 1, 7));
//        given(fileServiceMock.addFile(any(File.class))).willThrow(ValidationException.class);
//        mockMvc.perform(post(FILE_URL_QUERY)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(asJsonString(file)))
//                .andExpect(status().isBadRequest());
//    }

    /**
     * Update file success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void updateFileSuccess_1_MockTest() throws Exception {
//        file.setDate(LocalDate.of(2019, 1, 8));
//        doNothing().when(fileServiceMock).updateFile(any(File.class));
//        mockMvc.perform(put(FILE_URL_QUERY)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(asJsonString(file)))
//                .andExpect(status().isOk());
//    }

    /**
     * Update file un success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void updateFileUnSuccess_1_MockTest() throws Exception {
//        file.setDate(LocalDate.of(2019, 1, 9));
//        doThrow(ValidationException.class).when(fileServiceMock).updateFile(any(File.class));
//        mockMvc.perform(put(FILE_URL_QUERY)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(asJsonString(file)))
//                .andExpect(status().isBadRequest());
//    }

    /**
     * Update file un success 2 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void updateFileUnSuccess_2_MockTest() throws Exception {
//        file.setDate(LocalDate.of(2019, 1, 10));
//        doThrow(InternalServerException.class).when(fileServiceMock).updateFile(any(File.class));
//        mockMvc.perform(put(FILE_URL_QUERY)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(asJsonString(file)))
//                .andExpect(status().isInternalServerError());
//    }

    /**
     * Delete file success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void deleteFileSuccess_1_MockTest() throws Exception {
//        file.setDate(LocalDate.of(2019, 1, 11));
//        doNothing().when(fileServiceMock).deleteFile(anyLong());
//        mockMvc.perform(delete(FILE_ID_URL_QUERY, anyLong())
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk());
//    }

    /**
     * Delete file un success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void deleteFileUnSuccess_1_MockTest() throws Exception {
//        file.setDate(LocalDate.of(2019, 1, 12));
//        doThrow(ValidationException.class).when(fileServiceMock).deleteFile(anyLong());
//        mockMvc.perform(delete(FILE_ID_URL_QUERY, anyLong())
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isBadRequest());
//    }

    /**
     * Delete file un success 2 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void deleteFileUnSuccess_2_MockTest() throws Exception {
//        file.setDate(LocalDate.of(2019, 1, 13));
//        doThrow(InternalServerException.class).when(fileServiceMock).deleteFile(anyLong());
//        mockMvc.perform(delete(FILE_ID_URL_QUERY, anyLong())
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isInternalServerError());
//    }
}
