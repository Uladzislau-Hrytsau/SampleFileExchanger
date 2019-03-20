package com.exchange.dto.structure;

import com.exchange.dto.category.CategoryDto;
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
    private List<CategoryDto> categoryDtos;

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
     * @param categoryDtos        the category dtos
     */
    public StructureDto(List<FileStructureDto> fileStructureDtos, List<FolderStructureDto> folderStructureDtos, List<CategoryDto> categoryDtos) {
        this.fileStructureDtos = fileStructureDtos;
        this.folderStructureDtos = folderStructureDtos;
        this.categoryDtos = categoryDtos;
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

    /**
     * Gets category dtos.
     *
     * @return the category dtos
     */
    public List<CategoryDto> getCategoryDtos() {
        return categoryDtos;
    }

    /**
     * Sets category dtos.
     *
     * @param categoryDtos the category dtos
     */
    public void setCategoryDtos(List<CategoryDto> categoryDtos) {
        this.categoryDtos = categoryDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StructureDto that = (StructureDto) o;
        return Objects.equals(fileStructureDtos, that.fileStructureDtos) &&
                Objects.equals(folderStructureDtos, that.folderStructureDtos) &&
                Objects.equals(categoryDtos, that.categoryDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileStructureDtos, folderStructureDtos, categoryDtos);
    }

    @Override
    public String toString() {
        return "StructureDto{" +
                "fileStructureDtos=" + fileStructureDtos +
                ", folderStructureDtos=" + folderStructureDtos +
                ", categoryDtos=" + categoryDtos +
                '}';
    }
}
