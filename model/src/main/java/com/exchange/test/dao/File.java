package com.exchange.test.dao;

import java.sql.Date;
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

    private String category;

    public File() {
    }

    public File(Long id, Long user_id, String url, LocalDate date) {
        this.id = id;
        this.user_id = user_id;
        this.url = url;
        this.date = date;
    }

    public File(Long id, Long user_id, String url, String description, LocalDate date) {
        this(id, user_id, url, date);
        this.description = description;
    }

    public File(Long id, Long user_id, String url, String description, LocalDate date, String category) {
        this(id, user_id, url, description, date);
        this.category = category;
    }

    public File(Long id, Long user_id, String url, String description, String category) {
        this.id = id;
        this.user_id = user_id;
        this.url = url;
        this.description = description;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
                Objects.equals(category, file.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, url, description, date, category);
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", category='" + category + '\'' +
                '}';
    }
}
