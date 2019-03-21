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
     * Exists categories by user id boolean.
     *
     * @param categories the categories
     * @param userId     the user id
     * @return the boolean
     */
    Boolean existsCategoriesByUserId(Set<Long> categories, Long userId);

    /**
     * Add file categories int [ ].
     *
     * @param categoryDto the category dto
     * @return the int [ ]
     */
    int[] addFileCategories(Set<FileCategoryDto> categoryDto);

    /**
     * Gets categories by user id.
     *
     * @param userId the user id
     * @return the categories by user id
     */
    List<CategoryDto> getCategoriesByUserId(Long userId);

}
