package com.exchange.dao;

import java.util.List;

public interface CategoryDao {

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    boolean checkCategoryById(Long id);

}
