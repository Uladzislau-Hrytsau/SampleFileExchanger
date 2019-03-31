package com.exchange.dto.file;

import java.util.Objects;

/**
 * The type File structure dto.
 */
public class FileStructureDto {

    private Long id;
    private Long folderId;
    private String name;

    /**
     * Instantiates a new File structure dto.
     */
    public FileStructureDto() {
    }

    /**
     * Instantiates a new File structure dto.
     *
     * @param id       the id
     * @param folderId the folder id
     * @param name     the name
     */
    public FileStructureDto(
            final Long id,
            final Long folderId,
            final String name) {
        this.id = id;
        this.folderId = folderId;
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
    public void setId(final Long id) {
        this.id = id;
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
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileStructureDto fileDto = (FileStructureDto) o;
        return Objects.equals(id, fileDto.id) &&
                Objects.equals(folderId, fileDto.folderId) &&
                Objects.equals(name, fileDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, folderId, name);
    }

    @Override
    public String toString() {
        return "FileStructureDto{" +
                "id=" + id +
                ", folderId=" + folderId +
                ", name='" + name + '\'' +
                '}';
    }
}
