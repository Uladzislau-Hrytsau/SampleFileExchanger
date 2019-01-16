package com.exchange.dao;

import java.util.List;

/**
 * CategoryDao interface.
 * Created by Uladzislau Hrytsau on 27.11.18.
 */
public interface CategoryDao {

    /**
     * Get all categories list.
     *
     * @return categories list.
     */
    List<Category> getAllCategories();

    /**
     * Get category by category identifier.
     *
     * @param id category identifier.
     * @return category by identifier.
     */
    Category getCategoryById(Long id);

    /**
     * Check category by identifier.
     *
     * @param id category identifier.
     * @return boolean by identifier.
     */
    boolean checkCategoryById(Long id);

}
