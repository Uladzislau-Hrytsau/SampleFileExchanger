package com.exchange.dao;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * The type File.
 */
public class File {

    private Long id;
    private Long userId;
    private Long folderId;
    private String description;
    private String realName;
    private String encodeName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Set<Category> categories;

    /**
     * Instantiates a new File.
     */
    public File() {
    }

    /**
     * Instantiates a new File.
     *
     * @param userId     the user id
     * @param folderId   the Folder id
     * @param realName   the real name
     * @param encodeName the encode name
     * @param categories the categories
     */
    public File(Long userId, Long folderId, String realName, String encodeName, Set<Category> categories) {
        this.userId = userId;
        this.folderId = folderId;
        this.realName = realName;
        this.encodeName = encodeName;
        this.categories = categories;
    }

    /**
     * Instantiates a new File.
     *
     * @param id          the id
     * @param userId      the user id
     * @param folderId    the folder id
     * @param description the description
     * @param realName    the real name
     * @param encodeName  the encode name
     * @param date        the date
     * @param categories  the categories
     */
    public File(Long id, Long userId, Long folderId, String description, String realName, String encodeName, LocalDate date, Set<Category> categories) {
        this.id = id;
        this.userId = userId;
        this.folderId = folderId;
        this.description = description;
        this.realName = realName;
        this.encodeName = encodeName;
        this.date = date;
        this.categories = categories;
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
     * Gets Folder id.
     *
     * @return the Folder id
     */
    public Long getFolderId() {
        return folderId;
    }

    /**
     * Sets Folder id.
     *
     * @param folderId the Folder id
     */
    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets real name.
     *
     * @return the real name
     */
    public String getRealName() {
        return realName;
    }

    /**
     * Sets real name.
     *
     * @param realName the real name
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * Gets encode name.
     *
     * @return the encode name
     */
    public String getEncodeName() {
        return encodeName;
    }

    /**
     * Sets encode name.
     *
     * @param encodeName the encode name
     */
    public void setEncodeName(String encodeName) {
        this.encodeName = encodeName;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets categories.
     *
     * @return the categories
     */
    public Set<Category> getCategories() {
        return categories;
    }

    /**
     * Sets categories.
     *
     * @param categories the categories
     */
    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(id, file.id) &&
                Objects.equals(userId, file.userId) &&
                Objects.equals(folderId, file.folderId) &&
                Objects.equals(description, file.description) &&
                Objects.equals(realName, file.realName) &&
                Objects.equals(encodeName, file.encodeName) &&
                Objects.equals(date, file.date) &&
                Objects.equals(categories, file.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, folderId, description, realName, encodeName, date, categories);
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", userId=" + userId +
                ", folderId=" + folderId +
                ", description='" + description + '\'' +
                ", realName='" + realName + '\'' +
                ", encodeName='" + encodeName + '\'' +
                ", date=" + date +
                ", categories=" + categories +
                '}';
    }
}
