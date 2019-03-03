package com.exchange.dao;

import java.util.Objects;

/**
 * The type Category.
 */
public class Category {

    private Long userId;
    private Long categoryId;

    public Category() {
    }

    public Category(Long userId, Long categoryId) {
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(userId, category.userId) &&
                Objects.equals(categoryId, category.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, categoryId);
    }

    @Override
    public String toString() {
        return "Category{" +
                "userId=" + userId +
                ", categoryId=" + categoryId +
                '}';
    }
}
