package com.exchange.dao;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;

/**
 * The type File.
 */
public class File {

    private Long id;

    private Long userId;

    private String url;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    private Long categoryId;

    /**
     * Instantiates a new File.
     */
    public File() {
    }

    /**
     * Instantiates a new File.
     *
     * @param id     the id
     * @param userId the user id
     * @param url    the url
     * @param date   the date
     */
    public File(Long id, Long userId, String url, LocalDate date) {
        this.id = id;
        this.userId = userId;
        this.url = url;
        this.date = date;
    }

    /**
     * Instantiates a new File.
     *
     * @param id          the id
     * @param userId      the user id
     * @param url         the url
     * @param description the description
     * @param date        the date
     */
    public File(Long id, Long userId, String url, String description, LocalDate date) {
        this(id, userId, url, date);
        this.description = description;
    }

    /**
     * Instantiates a new File.
     *
     * @param id          the id
     * @param userId      the user id
     * @param url         the url
     * @param description the description
     * @param date        the date
     * @param categoryId  the category id
     */
    public File(Long id, Long userId, String url, String description, LocalDate date, Long categoryId) {
        this(id, userId, url, description, date);
        this.categoryId = categoryId;
    }

    /**
     * Instantiates a new File.
     *
     * @param id          the id
     * @param userId      the user id
     * @param url         the url
     * @param description the description
     * @param categoryId  the category id
     */
    public File(Long id, Long userId, String url, String description, Long categoryId) {
        this.id = id;
        this.userId = userId;
        this.url = url;
        this.description = description;
        this.categoryId = categoryId;
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
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
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
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(LocalDate date) {
        this.date = date;
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
        File file = (File) o;
        return Objects.equals(id, file.id) &&
                Objects.equals(userId, file.userId) &&
                Objects.equals(url, file.url) &&
                Objects.equals(description, file.description) &&
                Objects.equals(date, file.date) &&
                Objects.equals(categoryId, file.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, url, description, date, categoryId);
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", user_id=" + userId +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", categoryId=" + categoryId +
                '}';
    }
}
