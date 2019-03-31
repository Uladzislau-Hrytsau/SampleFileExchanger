package com.exchange.controller;

//@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerMockTest {
//    private static final Long ID_URL_PARAMETER = 1L;
//    private static final Long ONE_LONG = 1L;
//    private static final Long USER_ID = 1L;
//    private static final String USER_LOGIN = "userLogin";
//    private static final String USER_PASSWORD = "userPassword";
//    private static final Boolean USER_GENDER = true;
//    private static final String USER_INFORMATION = "userInformation";
//    private static final User user = new User(
//            USER_ID,
//            USER_LOGIN,
//            USER_PASSWORD,
//            USER_GENDER,
//            USER_INFORMATION);
//    private static final String USERS_URL = "/users";
//    private static final String USER_ID_URL = "/user/{id}";
//    private static final String USER_URL = "/user";
//    private static final String USER_LOGIN_LOGIN_URL = "/user/login/{login}";
//    @Mock
//    private UserService userServiceMock;
//    @InjectMocks
//    private UserRestController userRestController;
//    private MockMvc mockMvc;

    /**
     * Sets up.
     */
//    @Before
//    public void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(userRestController)
//                .setControllerAdvice(new RestErrorHandler())
//                .alwaysDo(print())
//                .build();
//    }

    /**
     * Gets all users success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void getAllUsersSuccess_1_MockTest() throws Exception {
//        user.setBirthDate(LocalDate.of(2019, 1, 1));
//        given(userServiceMock.getAllUsers()).
//                willReturn(Collections.singletonList(user));
//        mockMvc.perform(get(USERS_URL)
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(content().json(asJsonString(Collections.singletonList(user))));
//    }

    /**
     * Gets user by user id success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void getUserByUserIdSuccess_1_MockTest() throws Exception {
//        user.setBirthDate(LocalDate.of(2019, 1, 2));
//        given(userServiceMock.getUserByUserId(USER_ID)).willReturn(user);
//        mockMvc.perform(get(USER_ID_URL, ID_URL_PARAMETER)
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk())
//                .andExpect(content().json(asJsonString(user)));
//    }

    /**
     * Gets user by user id un success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void getUserByUserIdUnSuccess_1_MockTest() throws Exception {
//        user.setBirthDate(LocalDate.of(2019, 1, 2));
//        given(userServiceMock.getUserByUserId(USER_ID)).willReturn(user);
//        mockMvc.perform(get(USER_ID_URL, ID_URL_PARAMETER)
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk());
//    }

    /**
     * Gets user by user id un success 2 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void getUserByUserIdUnSuccess_2_MockTest() throws Exception {
//        user.setBirthDate(LocalDate.of(2019, 1, 3));
//        given(userServiceMock.getUserByUserId(anyLong())).willThrow(ValidationException.class);
//        mockMvc.perform(get(USER_ID_URL, ID_URL_PARAMETER)
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isBadRequest());
//    }

    /**
     * Gets user by login success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void getUserByLoginSuccess_1_MockTest() throws Exception {
//        user.setBirthDate(LocalDate.of(2019, 1, 4));
//        given(userServiceMock.getUserByLogin(anyString())).willReturn(user);
//        mockMvc.perform(get(USER_LOGIN_LOGIN_URL, USER_LOGIN)
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk())
//                .andExpect(content().json(asJsonString(user)));
//    }

    /**
     * Gets user by login un success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void getUserByLoginUnSuccess_1_MockTest() throws Exception {
//        user.setBirthDate(LocalDate.of(2019, 1, 5));
//        given(userServiceMock.getUserByLogin(anyString())).willThrow(ValidationException.class);
//        mockMvc.perform(get(USER_LOGIN_LOGIN_URL, USER_LOGIN)
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isBadRequest());
//    }

    /**
     * Add user success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void addUserSuccess_1_MockTest() throws Exception {
//        user.setBirthDate(LocalDate.of(2019, 1, 6));
//        given(userServiceMock.addUser(user)).willReturn(ONE_LONG);
//        mockMvc.perform(post(USER_URL)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(asJsonString(user)))
//                .andExpect(status().isCreated());
//    }

    /**
     * Add user un success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void addUserUnSuccess_1_MockTest() throws Exception {
//        user.setBirthDate(LocalDate.of(2019, 1, 7));
//        given(userServiceMock.addUser(user)).willThrow(ValidationException.class);
//        mockMvc.perform(post(USER_URL)
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(asJsonString(user)))
//                .andExpect(status().isBadRequest());
//    }

    /**
     * Update user success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void updateUserSuccess_1_MockTest() throws Exception {
//        user.setBirthDate(LocalDate.of(2019, 1, 8));
//        doNothing().when(userServiceMock).updateUser(user);
//        mockMvc.perform(put(USER_URL)
//                .content(asJsonString(user))
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk());
//    }

    /**
     * Update user un success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void updateUserUnSuccess_1_MockTest() throws Exception {
//        user.setBirthDate(LocalDate.of(2019, 1, 9));
//        doThrow(ValidationException.class).when(userServiceMock).updateUser(any(User.class));
//        mockMvc.perform(put(USER_URL)
//                .content(asJsonString(user))
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isBadRequest());
//    }

    /**
     * Update user un success 2 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void updateUserUnSuccess_2_MockTest() throws Exception {
//        user.setBirthDate(LocalDate.of(2019, 1, 10));
//        doThrow(InternalServerException.class).when(userServiceMock).updateUser(any(User.class));
//        mockMvc.perform(put(USER_URL)
//                .content(asJsonString(user))
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isInternalServerError());
//    }

    /**
     * Delete user success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void deleteUserSuccess_1_MockTest() throws Exception {
//        user.setBirthDate(LocalDate.of(2019, 1, 11));
//        given(userServiceMock.addUser(user)).willReturn(ONE_LONG);
//        mockMvc.perform(delete(USER_ID_URL, ID_URL_PARAMETER)
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isOk());
//    }

    /**
     * Delete user un success 1 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void deleteUserUnSuccess_1_MockTest() throws Exception {
//        user.setBirthDate(LocalDate.of(2019, 1, 11));
//        given(userServiceMock.addUser(user)).willReturn(ONE_LONG);
//        doThrow(ValidationException.class).when(userServiceMock).deleteUser(ONE_LONG);
//        mockMvc.perform(delete(USER_ID_URL, ID_URL_PARAMETER)
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isBadRequest());
//    }

    /**
     * Delete user un success 2 mock test.
     *
     * @throws Exception the exception
     */
//    @Test
//    public void deleteUserUnSuccess_2_MockTest() throws Exception {
//        user.setBirthDate(LocalDate.of(2019, 1, 12));
//        given(userServiceMock.addUser(user)).willReturn(ONE_LONG);
//        doThrow(InternalServerException.class).when(userServiceMock).deleteUser(ONE_LONG);
//        mockMvc.perform(delete(USER_ID_URL, ID_URL_PARAMETER)
//                .contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(status().isInternalServerError());
//
//    }


}
