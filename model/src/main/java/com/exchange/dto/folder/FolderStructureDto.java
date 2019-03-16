package com.exchange.dto.folder;

import java.util.Objects;

/**
 * The type Folder structure dto.
 */
public class FolderStructureDto {

    private Long id;
    private String name;

    /**
     * Instantiates a new Folder structure dto.
     */
    public FolderStructureDto() {
    }

    /**
     * Instantiates a new Folder structure dto.
     *
     * @param id   the id
     * @param name the name
     */
    public FolderStructureDto(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FolderStructureDto that = (FolderStructureDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "FolderStructureDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

