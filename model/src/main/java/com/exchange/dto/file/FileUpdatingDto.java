package com.exchange.dto.file;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;

/**
 * The type File updating dto.
 */
public class FileUpdatingDto {

    private Long id;
    private String description;
    private String realName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    /**
     * Instantiates a new File updating dto.
     */
    public FileUpdatingDto() {
    }

    /**
     * Instantiates a new File updating dto.
     *
     * @param id          the id
     * @param description the description
     * @param realName    the real name
     * @param date        the date
     */
    public FileUpdatingDto(Long id, String description, String realName, LocalDate date) {
        this.id = id;
        this.description = description;
        this.realName = realName;
        this.date = date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileUpdatingDto that = (FileUpdatingDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(realName, that.realName) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, realName, date);
    }

    @Override
    public String toString() {
        return "FileUpdatingDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", realName='" + realName + '\'' +
                ", date=" + date +
                '}';
    }
}
