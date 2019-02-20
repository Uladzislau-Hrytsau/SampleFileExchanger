package com.exchange.dao;

import java.util.Objects;

/**
 * The type Category.
 */
public class Category {

    private Long id;

    private String fileCategory;

    /**
     * Instantiates a new Category.
     */
    public Category() {
    }

    /**
     * Instantiates a new Category.
     *
     * @param id           the id
     * @param fileCategory the file category
     */
    public Category(Long id, String fileCategory) {
        this.id = id;
        this.fileCategory = fileCategory;
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
     * Gets file category.
     *
     * @return the file category
     */
    public String getFileCategory() {
        return fileCategory;
    }

    /**
     * Sets file category.
     *
     * @param fileCategory the file category
     */
    public void setFileCategory(String fileCategory) {
        this.fileCategory = fileCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category1 = (Category) o;
        return Objects.equals(id, category1.id) &&
                Objects.equals(fileCategory, category1.fileCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileCategory);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", category='" + fileCategory + '\'' +
                '}';
    }
}
