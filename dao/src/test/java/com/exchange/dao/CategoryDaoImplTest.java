package com.exchange.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Uladzislau Hrytsau on 9.12.18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class CategoryDaoImplTest {

    /**
     * The Category dao.
     */
    @Autowired
    private CategoryDao categoryDao;

    /**
     * Gets all categories test.
     */
    @Test
    public void getAllCategoriesTest() {
        List<Category> categories = categoryDao.getAllCategories();
        assertNotNull(categories);
        assertEquals(3, categories.size());
    }

    /**
     * Gets category by id test.
     */
    @Test
    public void getCategoryByIdTest() {
        assertEquals("default", categoryDao.getCategoryById(1L).getCategory());
    }

    /**
     * Check category by id test.
     */
    @Test
    public void checkCategoryByIdTest() {
        assertTrue(categoryDao.checkCategoryById(2L));
    }

}
