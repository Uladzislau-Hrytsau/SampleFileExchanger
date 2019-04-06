package com.exchange.service.validation;

import com.exchange.dao.CategoryDao;
import com.exchange.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * The type Category validator.
 */
@Component
public class CategoryValidator {

    private final CategoryDao categoryDao;

    /**
     * Instantiates a new Category validator.
     *
     * @param categoryDao the category dao
     */
    @Autowired
    public CategoryValidator(final CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * Validate categories by user id.
     *
     * @param categories the categories
     * @param userId     the user id
     */
    public void validateCategoriesByUserId(final Set<Long> categories, final Long userId) {
        categories.forEach(item -> {
            if (item == null || item < 0L) {
                throw new ValidationException();
            }
        });
        if (!categoryDao.existsCategoriesByUserId(categories, userId)) {
            throw new ValidationException();
        }
    }

}
