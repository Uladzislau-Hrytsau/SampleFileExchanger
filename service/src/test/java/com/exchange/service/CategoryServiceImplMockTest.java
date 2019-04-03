package com.exchange.service;

import com.exchange.dao.CategoryDao;
import com.exchange.dto.file.FileCategoryDto;
import com.exchange.exception.InternalServerException;
import com.exchange.exception.ValidationException;
import com.exchange.service.implementation.CategoryServiceImpl;
import com.exchange.service.implementation.CommonService;
import com.exchange.service.validation.category.CategoryValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplMockTest {

    private static final int[] CORRECT_BATCH_RESULT = {1, 1, 1};
    private static final Long CORRECT_FILE_ID = 1L;
    private static final Long CORRECT_USER_ID = 1L;
    private static final Set<Long> CORRECT_CATEGORIES = new HashSet<>(
            Arrays.asList(1L, 2L, 3L)
    );

    @Mock
    private CategoryDao categoryDaoMock;
    @Mock
    private CategoryValidator categoryValidatorMock;
    @Mock
    private CommonService commonServiceMock;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    public void addFileCategories_correctCategoriesAndFileIdAndUserId_correct() {
        doNothing().when(categoryValidatorMock).validateCategoriesByUserId(anySet(), anyLong());
        when(categoryDaoMock.addFileCategories(anySet())).thenReturn(any());
        when(commonServiceMock.checkBatchResult(CORRECT_BATCH_RESULT)).thenReturn(Boolean.TRUE);
        categoryService.addFileCategories(CORRECT_CATEGORIES, CORRECT_FILE_ID, CORRECT_USER_ID);
    }

    @Test(expected = ValidationException.class)
    public void addFileCategories_incorrectCategoriesAndFileIdAndUserId_validationException() {
        doThrow(ValidationException.class).when(categoryValidatorMock).validateCategoriesByUserId(anySet(), anyLong());
        categoryService.addFileCategories(CORRECT_CATEGORIES, CORRECT_FILE_ID, CORRECT_USER_ID);
        verifyNoMoreInteractions(categoryDaoMock, categoryValidatorMock, commonServiceMock);
    }

    @Test(expected = InternalServerException.class)
    public void addFileCategories_correctCategoriesAndUserIdAndIncorrectFileId_internalServerException() {
        doNothing().when(categoryValidatorMock).validateCategoriesByUserId(anySet(), anyLong());
        when(categoryDaoMock.addFileCategories(anySet())).thenReturn(any());
        when(commonServiceMock.checkBatchResult(CORRECT_BATCH_RESULT)).thenReturn(Boolean.FALSE);
        categoryService.addFileCategories(CORRECT_CATEGORIES, CORRECT_FILE_ID, CORRECT_USER_ID);
        verifyNoMoreInteractions(categoryDaoMock, categoryValidatorMock, commonServiceMock);
    }

    @Test
    public void getFileCategoryDtosByCategoriesAndFileId_correctCategoriesAndFileId_correctFileCategoryDtos() {
        Set<FileCategoryDto> expectedFileCategoryDtos = new HashSet<>();
        CORRECT_CATEGORIES.forEach(item -> expectedFileCategoryDtos.add(new FileCategoryDto(item, CORRECT_FILE_ID)));
        Set<FileCategoryDto> actualFileCategoryDtos = categoryService.getFileCategoryDtosByCategoriesAndFileId(
                CORRECT_CATEGORIES, CORRECT_FILE_ID);
        Assert.assertEquals(expectedFileCategoryDtos, actualFileCategoryDtos);
    }
}
