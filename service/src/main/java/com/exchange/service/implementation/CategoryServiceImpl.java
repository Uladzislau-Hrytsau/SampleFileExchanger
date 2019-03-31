package com.exchange.service.implementation;

import com.exchange.dao.CategoryDao;
import com.exchange.dto.file.FileCategoryDto;
import com.exchange.exception.InternalServerException;
import com.exchange.service.CategoryService;
import com.exchange.service.validation.category.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final CommonService commonService;

    @Value("${categoryService.createError}")
    private String createError;

    /**
     * Instantiates a new Category service.
     *
     * @param categoryDao       the category dao
     * @param categoryValidator the category validator
     * @param commonService     the common service
     */
    @Autowired
    public CategoryServiceImpl(
            final CategoryDao categoryDao,
            final CategoryValidator categoryValidator,
            final CommonService commonService) {
        this.categoryDao = categoryDao;
        this.categoryValidator = categoryValidator;
        this.commonService = commonService;
    }

    @Override
    public void addFileCategories(
            final Set<Long> categories,
            final Long fileId,
            final Long userId) {
        categoryValidator.validateCategoriesByUserId(categories, userId);
        int[] batchResult = categoryDao.addFileCategories(this.getFileCategoryDtosByCategoriesAndFileId(categories, fileId));
        if (!commonService.checkBatchResult(batchResult)) {
            throw new InternalServerException(createError);
        }
    }

    /**
     * Gets file category dtos by categories and file id.
     *
     * @param categories the categories
     * @param fileId     the file id
     * @return the file category dtos by categories and file id
     */
    public Set<FileCategoryDto> getFileCategoryDtosByCategoriesAndFileId(
            final Set<Long> categories,
            final Long fileId) {
        Set<FileCategoryDto> fileCategoryDtos = new HashSet<>(categories.size());
        categories.forEach(item -> fileCategoryDtos.add(new FileCategoryDto(item, fileId)));
        return fileCategoryDtos;
    }

}
