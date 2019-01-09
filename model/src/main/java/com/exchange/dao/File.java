package com.exchange.dao;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Created by Uladzislau Hrytsau on 23.11.18.
 */
public class File {

    private Long id;

    private Long user_id;

    private String url;

    private String description;

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
     * @param id      the id
     * @param user_id the user id
     * @param url     the url
     * @param date    the date
     */
    public File(Long id, Long user_id, String url, LocalDate date) {
        this.id = id;
        this.user_id = user_id;
        this.url = url;
        this.date = date;
    }

    /**
     * Instantiates a new File.
     *
     * @param id          the id
     * @param user_id     the user id
     * @param url         the url
     * @param description the description
     * @param date        the date
     */
    public File(Long id, Long user_id, String url, String description, LocalDate date) {
        this(id, user_id, url, date);
        this.description = description;
    }

    /**
     * Instantiates a new File.
     *
     * @param id          the id
     * @param user_id     the user id
     * @param url         the url
     * @param description the description
     * @param date        the date
     * @param categoryId  the category id
     */
    public File(Long id, Long user_id, String url, String description, LocalDate date, Long categoryId) {
        this(id, user_id, url, description, date);
        this.categoryId = categoryId;
    }

    /**
     * Instantiates a new File.
     *
     * @param id          the id
     * @param user_id     the user id
     * @param url         the url
     * @param description the description
     * @param categoryId  the category id
     */
    public File(Long id, Long user_id, String url, String description, Long categoryId) {
        this.id = id;
        this.user_id = user_id;
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
    public Long getUser_id() {
        return user_id;
    }

    /**
     * Sets user id.
     *
     * @param user_id the user id
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
                Objects.equals(user_id, file.user_id) &&
                Objects.equals(url, file.url) &&
                Objects.equals(description, file.description) &&
                Objects.equals(date, file.date) &&
                Objects.equals(categoryId, file.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, url, description, date, categoryId);
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", categoryId=" + categoryId +
                '}';
    }
}
