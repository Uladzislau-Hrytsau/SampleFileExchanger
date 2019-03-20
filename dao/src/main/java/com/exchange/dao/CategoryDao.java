package com.exchange.dao;

import com.exchange.dto.category.CategoryDto;
import com.exchange.dto.file.FileCategoryDto;

import java.util.List;
import java.util.Set;

/**
 * The interface Category dao.
 */
public interface CategoryDao {

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

    /**
     * Gets categories by user id.
     *
     * @param userId the user id
     * @return the categories by user id
     */
    List<CategoryDto> getCategoriesByUserId(Long userId);

}
