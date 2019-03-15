package com.exchange.dto;

import java.util.Objects;

/**
 * The type File structure dto.
 */
public class FileStructureDto {

    private Long id;
    private Long folderId;
    private String realName;

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
     * @param realName the real name
     */
    public FileStructureDto(Long id, Long folderId, String realName) {
        this.id = id;
        this.folderId = folderId;
        this.realName = realName;
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
    public void setFolderId(Long folderId) {
        this.folderId = folderId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileStructureDto fileDto = (FileStructureDto) o;
        return Objects.equals(id, fileDto.id) &&
                Objects.equals(folderId, fileDto.folderId) &&
                Objects.equals(realName, fileDto.realName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, folderId, realName);
    }

    @Override
    public String toString() {
        return "FileStructureDto{" +
                "id=" + id +
                ", folderId=" + folderId +
                ", realName='" + realName + '\'' +
                '}';
    }
}
