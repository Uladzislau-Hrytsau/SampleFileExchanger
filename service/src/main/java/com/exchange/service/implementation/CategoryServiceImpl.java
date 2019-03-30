package com.exchange.service.implementation;

import com.exchange.dao.CategoryDao;
import com.exchange.dto.file.FileCategoryDto;
import com.exchange.service.CategoryService;
import com.exchange.service.validation.category.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Category service.
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;
    private final CategoryValidator categoryValidator;

    /**
     * Instantiates a new Category service.
     *
     * @param categoryDao       the category dao
     * @param categoryValidator the category validator
     */
    @Autowired
    public CategoryServiceImpl(
            CategoryDao categoryDao,
            CategoryValidator categoryValidator) {
        this.categoryDao = categoryDao;
        this.categoryValidator = categoryValidator;
    }

    @Override
    public void addFileCategories(Set<Long> categories, Long fileId, Long userId) {
        categoryValidator.validateCategoriesByUserId(categories, userId);
        categoryDao.addFileCategories(this.getFileCategoryDtosByCategoriesAndFileId(categories, fileId));
    }

    /**
     * Gets file category dtos by categories and file id.
     *
     * @param categories the categories
     * @param fileId     the file id
     * @return the file category dtos by categories and file id
     */
    public Set<FileCategoryDto> getFileCategoryDtosByCategoriesAndFileId(Set<Long> categories, Long fileId) {
        Set<FileCategoryDto> fileCategoryDtos = new HashSet<>(categories.size());
        categories.forEach(item -> fileCategoryDtos.add(new FileCategoryDto(item, fileId)));
        return fileCategoryDtos;
    }

}
