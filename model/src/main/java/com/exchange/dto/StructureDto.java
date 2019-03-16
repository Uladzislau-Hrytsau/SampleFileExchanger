package com.exchange.dto;

import com.exchange.dto.file.FileStructureDto;
import com.exchange.dto.folder.FolderStructureDto;

import java.util.List;
import java.util.Objects;

/**
 * The type Structure dto.
 */
public class StructureDto {

    private List<FileStructureDto> fileStructureDtos;
    private List<FolderStructureDto> folderStructureDtos;

    /**
     * Instantiates a new Structure dto.
     */
    public StructureDto() {
    }

    /**
     * Instantiates a new Structure dto.
     *
     * @param fileStructureDtos   the file structure dtos
     * @param folderStructureDtos the folder structure dtos
     */
    public StructureDto(List<FileStructureDto> fileStructureDtos, List<FolderStructureDto> folderStructureDtos) {
        this.fileStructureDtos = fileStructureDtos;
        this.folderStructureDtos = folderStructureDtos;
    }

    /**
     * Gets file structure dtos.
     *
     * @return the file structure dtos
     */
    public List<FileStructureDto> getFileStructureDtos() {
        return fileStructureDtos;
    }

    /**
     * Sets file structure dtos.
     *
     * @param fileStructureDtos the file structure dtos
     */
    public void setFileStructureDtos(List<FileStructureDto> fileStructureDtos) {
        this.fileStructureDtos = fileStructureDtos;
    }

    /**
     * Gets folder structure dtos.
     *
     * @return the folder structure dtos
     */
    public List<FolderStructureDto> getFolderStructureDtos() {
        return folderStructureDtos;
    }

    /**
     * Sets folder structure dtos.
     *
     * @param folderStructureDtos the folder structure dtos
     */
    public void setFolderStructureDtos(List<FolderStructureDto> folderStructureDtos) {
        this.folderStructureDtos = folderStructureDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StructureDto that = (StructureDto) o;
        return Objects.equals(fileStructureDtos, that.fileStructureDtos) &&
                Objects.equals(folderStructureDtos, that.folderStructureDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileStructureDtos, folderStructureDtos);
    }

    @Override
    public String toString() {
        return "StructureDto{" +
                "fileStructureDtos=" + fileStructureDtos +
                ", folderStructureDtos=" + folderStructureDtos +
                '}';
    }
}
