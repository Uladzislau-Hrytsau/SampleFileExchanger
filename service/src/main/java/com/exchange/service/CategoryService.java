package com.exchange.service;

import java.util.Set;

/**
 * The interface Category service.
 */
public interface CategoryService {

    /**
     * Add file categories.
     *
     * @param categories the categories
     * @param fileId     the file id
     * @param userId     the user id
     */
    void addFileCategories(Set<Long> categories, Long fileId, Long userId);

}
