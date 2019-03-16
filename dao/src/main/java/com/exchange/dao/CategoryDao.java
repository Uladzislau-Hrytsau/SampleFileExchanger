package com.exchange.dao;

import com.exchange.dto.file.FileCategoryDto;

import java.util.List;
import java.util.Set;

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
    Boolean checkCategoryById(Long id);

    /**
     * Exists by categories boolean.
     *
     * @param categories the categories
     * @param userId     the user id
     * @return the boolean
     */
    Boolean existsByCategories(Set<Long> categories, Long userId);

    /**
     * Add file categories int [ ].
     *
     * @param categories the categories
     * @return the int [ ]
     */
    int[] addFileCategories(Set<FileCategoryDto> categories);

}
