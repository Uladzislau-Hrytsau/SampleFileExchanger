package com.exchange.dto.file;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * The type File dto.
 */
public class FileDto {

    private Long userId;
    private Long folderId;
    private String description;
    private String realName;
    private String encodeName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private Set<Long> categories;

    /**
     * Instantiates a new File dto.
     */
    public FileDto() {
    }

    /**
     * Instantiates a new File dto.
     *
     * @param userId      the user id
     * @param folderId    the folder id
     * @param description the description
     * @param realName    the real name
     * @param encodeName  the encode name
     * @param date        the date
     * @param categories  the categories
     */
    public FileDto(
            final Long userId,
            final Long folderId,
            final String description,
            final String realName,
            final String encodeName,
            final LocalDate date,
            final Set<Long> categories) {
        this.userId = userId;
        this.folderId = folderId;
        this.description = description;
        this.realName = realName;
        this.encodeName = encodeName;
        this.date = date;
        this.categories = categories;
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
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * Gets folder id.
     *
     * @return the folder id
     */
    public Long getFolderId() {
        return folderId;
    }

    /**
     * Sets folder id.
     *
     * @param folderId the folder id
     */
    public void setFolderId(final Long folderId) {
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
    public void setDescription(final String description) {
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
    public void setRealName(final String realName) {
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
    public void setEncodeName(final String encodeName) {
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
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(final LocalDate date) {
        this.date = date;
    }

    /**
     * Gets categories.
     *
     * @return the categories
     */
    public Set<Long> getCategories() {
        return categories;
    }

    /**
     * Sets categories.
     *
     * @param categories the categories
     */
    public void setCategories(final Set<Long> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDto fileDto = (FileDto) o;
        return Objects.equals(userId, fileDto.userId) &&
                Objects.equals(folderId, fileDto.folderId) &&
                Objects.equals(description, fileDto.description) &&
                Objects.equals(realName, fileDto.realName) &&
                Objects.equals(encodeName, fileDto.encodeName) &&
                Objects.equals(date, fileDto.date) &&
                Objects.equals(categories, fileDto.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, folderId, description, realName, encodeName, date, categories);
    }

    @Override
    public String toString() {
        return "FileDto{" +
                "userId=" + userId +
                ", folderId=" + folderId +
                ", description='" + description + '\'' +
                ", realName='" + realName + '\'' +
                ", encodeName='" + encodeName + '\'' +
                ", date=" + date +
                ", categories=" + categories +
                '}';
    }
}
