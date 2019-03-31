package com.exchange.dao.jdbc;

import com.exchange.dao.CategoryDao;
import com.exchange.dao.TestSpringDaoConfiguration;
import com.exchange.dto.category.CategoryDto;
import com.exchange.dto.file.FileCategoryDto;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestSpringDaoConfiguration.class)
@Rollback
public class CategoryDaoImplTest {

    private static final Long CORRECT_USER_ID_ONE = 1L;
    private static final Long CORRECT_USER_ID_TWO = 2L;
    private static final Long INCORRECT_NEGATIVE_USER_ID = -4L;
    private static final Long CORRECT_CATEGORY_ONE = 1L;
    private static final Long CORRECT_CATEGORY_TWO = 2L;
    private static final Long CORRECT_CATEGORY_THREE = 3L;
    private static final Long CORRECT_FILE_ID_FIVE = 5L;
    private static final Long NEGATIVE_FILE_ID = -1L;

    private static final Set<FileCategoryDto> CORRECT_FILE_CATEGORY_DTOS = new HashSet<>(Arrays.asList(
            new FileCategoryDto(CORRECT_CATEGORY_ONE, CORRECT_FILE_ID_FIVE),
            new FileCategoryDto(CORRECT_CATEGORY_TWO, CORRECT_FILE_ID_FIVE),
            new FileCategoryDto(CORRECT_CATEGORY_THREE, CORRECT_FILE_ID_FIVE)));
    private static final Set<FileCategoryDto> INCORRECT_FILE_CATEGORY_DTOS = new HashSet<>(Collections.singletonList(
            new FileCategoryDto(CORRECT_CATEGORY_ONE, NEGATIVE_FILE_ID)));

    @Autowired
    private CategoryDao categoryDao;

    @Before
    public void setUp() {

    }

    @Test
    public void existsCategoriesByUserId_exists_TrueReturned() {
        Set<Long> categories = new HashSet<>(Arrays.asList(CORRECT_CATEGORY_ONE, CORRECT_CATEGORY_TWO, CORRECT_CATEGORY_THREE));
        assertTrue(categoryDao.existsCategoriesByUserId(categories, CORRECT_USER_ID_ONE));
    }

    @Test
    public void existsCategoriesByUserId_doesNotExist_FalseReturned() {
        Set<Long> categories = new HashSet<>(Arrays.asList(CORRECT_CATEGORY_ONE, CORRECT_CATEGORY_TWO, CORRECT_CATEGORY_THREE));
        assertFalse(categoryDao.existsCategoriesByUserId(categories, CORRECT_USER_ID_TWO));
    }

    @Test(expected = NullPointerException.class)
    public void existsCategoriesByUserId_nullCategories_FalseReturned() {
        assertFalse(categoryDao.existsCategoriesByUserId(null, CORRECT_USER_ID_TWO));
    }

    @Test
    public void existsCategoriesByUserId_nullUserId_FalseReturned() {
        Set<Long> categories = new HashSet<>(Arrays.asList(CORRECT_CATEGORY_ONE, CORRECT_CATEGORY_TWO, CORRECT_CATEGORY_THREE));
        assertFalse(categoryDao.existsCategoriesByUserId(categories, null));
    }

    @Test
    public void addFileCategories_correctFileCategories_correctArrayReturned() {
        int[] batchResult = categoryDao.addFileCategories(CORRECT_FILE_CATEGORY_DTOS);
        assertNotNull(batchResult);
        Integer expectedSize = CORRECT_FILE_CATEGORY_DTOS.size();
        Integer actualSize = batchResult.length;
        assertEquals(expectedSize, actualSize);
        assertTrue(checkBatchResult(batchResult));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void addFileCategories_incorrectFileId_DataIntegrityViolationExceptionAndIncorrectArrayReturned() {
        int[] batchResult = categoryDao.addFileCategories(INCORRECT_FILE_CATEGORY_DTOS);
        assertNotNull(batchResult);
        Integer expectedSize = INCORRECT_FILE_CATEGORY_DTOS.size();
        Integer actualSize = batchResult.length;
        assertNotEquals(expectedSize, actualSize);
        assertFalse(checkBatchResult(batchResult));
    }

    @Test(expected = NullPointerException.class)
    public void addFileCategories_nullFileCategoryDtos_NullPointerExceptionAndIncorrectArrayReturned() {
        int[] batchResult = categoryDao.addFileCategories(null);
        assertNotNull(batchResult);
        Integer expectedSize = 0;
        Integer actualSize = batchResult.length;
        assertEquals(expectedSize, actualSize);
        assertFalse(checkBatchResult(batchResult));
    }

    @Test
    public void getCategoriesByUserId_correctUserId_correctCategoriesReturned() {
        Integer expectedSize = 3;
        List<CategoryDto> categoryDtos = categoryDao.getCategoriesByUserId(CORRECT_USER_ID_ONE);
        assertNotNull(categoryDtos);
        Integer actualSize = categoryDtos.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void getCategoriesByUserId_notExistsUserId_zeroCategoriesReturned() {
        Integer expectedSize = 0;
        List<CategoryDto> categoryDtos = categoryDao.getCategoriesByUserId(INCORRECT_NEGATIVE_USER_ID);
        assertNotNull(categoryDtos);
        Integer actualSize = categoryDtos.size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void getCategoriesByUserId_nullUserId_incorrectCategoriesReturned() {
        Integer expectedSize = 0;
        List<CategoryDto> categoryDtos = categoryDao.getCategoriesByUserId(null);
        assertNotNull(categoryDtos);
        Integer actualSize = categoryDtos.size();
        assertEquals(expectedSize, actualSize);
    }

    private Boolean checkBatchResult(final int[] batchResult) {
        for (int result : batchResult) {
            if (result == 0) {
                return false;
            }
        }
        return true;
    }

}