package com.exchange.dao;

import java.util.Objects;

/**
 * The type Category.
 */
public class Category {

    private Long userId;
    private Long categoryId;

    /**
     * Instantiates a new Category.
     */
    public Category() {
    }

    /**
     * Instantiates a new Category.
     *
     * @param userId     the user id
     * @param categoryId the category id
     */
    public Category(Long userId, Long categoryId) {
        this.userId = userId;
        this.categoryId = categoryId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets category id.
     *
     * @return the category id
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * Sets category id.
     *
     * @param categoryId the category id
     */
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
