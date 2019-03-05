package com.exchange.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

/**
 * The type Category dao impl test.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class CategoryDaoImplTest {

    @Autowired
    private CategoryDao categoryDao;

    /**
     * Test.
     */
    @Test
    public void test() {
        Set<Long> set = new HashSet<>();
        set.add(1L);
        set.add(2L);
        assertTrue(categoryDao.existsByCategories(set, 1L));
    }

    /**
     * Gets all categories test.
     */
//    @Test
//    public void getAllCategoriesTest() {
//        List<Category> categories = categoryDao.getAllCategories();
//        assertNotNull(categories);
//        assertEquals(3, categories.size());
//    }

    /**
     * Gets category by id test.
     */
//    @Test
//    public void getCategoryByIdTest() {
//        assertEquals("default", categoryDao.getCategoryById(1L).getFileCategory());
//    }

    /**
     * Check category by id test.
     */
//    @Test
//    public void checkCategoryByIdTest() {
//        assertTrue(categoryDao.checkCategoryById(2L));
//    }

}
