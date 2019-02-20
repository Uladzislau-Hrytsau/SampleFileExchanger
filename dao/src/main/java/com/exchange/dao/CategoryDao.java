package com.exchange.dao;

import java.util.List;

/**
 * The interface Category dao.
 */
public interface CategoryDao {

    /**
     * Gets all categories.
     *
     * @return the all categories
     */
    List<Category> getAllCategories();

    /**
     * Gets category by id.
     *
     * @param id the id
     * @return the category by id
     */
    Category getCategoryById(Long id);

    /**
     * Check category by id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean checkCategoryById(Long id);

}
