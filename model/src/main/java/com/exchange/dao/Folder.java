package com.exchange.dao;

import java.util.Objects;

/**
 * The type Folder.
 */
public class Folder {

    private Long id;
    private String name;
    private Long userId;
    private Long parentId;

    /**
     * Instantiates a new Folder.
     */
    public Folder() {
    }

    /**
     * Instantiates a new Folder.
     *
     * @param id       the id
     * @param name     the name
     * @param userId   the user id
     * @param parentId the parent id
     */
    public Folder(Long id, String name, Long userId, Long parentId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.parentId = parentId;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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
     * Gets parent id.
     *
     * @return the parent id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * Sets parent id.
     *
     * @param parentId the parent id
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Folder folder = (Folder) o;
        return Objects.equals(id, folder.id) &&
                Objects.equals(name, folder.name) &&
                Objects.equals(userId, folder.userId) &&
                Objects.equals(parentId, folder.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, userId, parentId);
    }

    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                ", parentId=" + parentId +
                '}';
    }
}
