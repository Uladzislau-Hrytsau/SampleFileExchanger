package com.exchange.service.validation;

import com.exchange.dao.CategoryDao;
import com.exchange.exception.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;

/**
 * The type Category validator mock test.
 */
@RunWith(MockitoJUnitRunner.class)
public class CategoryValidatorMockTest {

    private static final Integer TIMES_ONE = 1;
    private static final Set<Long> CORRECT_CATEGORIES = new HashSet<>(
            Arrays.asList(1L, 2L, 3L)
    );
    private static final Long CORRECT_USER_ID = 1L;
    private static final Long INCORRECT_USER_ID = -1L;

    @Mock
    private CategoryDao categoryDaoMock;
    @InjectMocks
    private CategoryValidator categoryValidator;

    /**
     * Validate categories by user id with correct categories and user id then correct.
     */
    @Test
    public void validateCategoriesByUserIdWithCorrectCategoriesAndUserIdThenCorrect() {
        when(categoryDaoMock.existsCategoriesByUserId(anySet(), anyLong())).thenReturn(Boolean.TRUE);
        categoryValidator.validateCategoriesByUserId(CORRECT_CATEGORIES, CORRECT_USER_ID);
        verify(categoryDaoMock, times(TIMES_ONE)).existsCategoriesByUserId(anySet(), anyLong());
        verifyNoMoreInteractions(categoryDaoMock);
    }

    /**
     * Validate categories by user id with incorrect categories and correct user id then throw validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validateCategoriesByUserIdWithIncorrectCategoriesAndCorrectUserIdThenThrowValidationException() {
        categoryValidator.validateCategoriesByUserId(anySet(), anyLong());
        verify(categoryDaoMock, never()).existsCategoriesByUserId(anySet(), anyLong());
        verifyNoMoreInteractions(categoryDaoMock);
    }

    /**
     * Validate categories by user id with correct categories and user id then throw validation exception.
     */
    @Test(expected = ValidationException.class)
    public void validateCategoriesByUserIdWithCorrectCategoriesAndUserIdThenThrowValidationException() {
        when(categoryDaoMock.existsCategoriesByUserId(anySet(), anyLong())).thenReturn(Boolean.FALSE);
        categoryValidator.validateCategoriesByUserId(CORRECT_CATEGORIES, INCORRECT_USER_ID);
        verify(categoryDaoMock, times(TIMES_ONE)).existsCategoriesByUserId(anySet(), anyLong());
        verifyNoMoreInteractions(categoryDaoMock);
    }

}
