package com.exchange.dao;

import java.util.Objects;

/**
 * Created by Uladzislau Hrytsau on 23.11.18.
 */
public class Category {

    private Long id;

    private String category;

    /**
     * Instantiates a new Category.
     */
    public Category() {
    }

    /**
     * Instantiates a new Category.
     *
     * @param id       the id
     * @param category the category
     */
    public Category(Long id, String category) {
        this.id = id;
        this.category = category;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category1 = (Category) o;
        return Objects.equals(id, category1.id) &&
                Objects.equals(category, category1.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", category='" + category + '\'' +
                '}';
    }
}
