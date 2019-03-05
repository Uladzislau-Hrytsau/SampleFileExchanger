package com.exchange.dto;

import java.util.Objects;

/**
 * The type File category dto.
 */
public class FileCategoryDto {

    private Long categoryId;
    private Long fileId;

    /**
     * Instantiates a new File category dto.
     */
    public FileCategoryDto() {
    }

    /**
     * Instantiates a new File category dto.
     *
     * @param categoryId the category id
     * @param fileId     the file id
     */
    public FileCategoryDto(Long categoryId, Long fileId) {
        this.categoryId = categoryId;
        this.fileId = fileId;
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

    /**
     * Gets file id.
     *
     * @return the file id
     */
    public Long getFileId() {
        return fileId;
    }

    /**
     * Sets file id.
     *
     * @param fileId the file id
     */
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileCategoryDto that = (FileCategoryDto) o;
        return Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(fileId, that.fileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, fileId);
    }

    @Override
    public String toString() {
        return "FileCategoryDto{" +
                "categoryId=" + categoryId +
                ", fileId=" + fileId +
                '}';
    }
}

