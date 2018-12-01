package com.exchange.test.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Uladzislau Hrytsau on 28.11.18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test-spring-dao.xml"})
@Transactional
public class FileDaoImplTest {

    private static final Long ID_1 = 1L;

    private static final File file_1 = new File(
            3L, 1L, "url3", "description3",
            LocalDate.of(999, 1, 1), "1"
    );
    private static final File file_2 = new File(
            4L, 2L, "url4", "description4",
            LocalDate.of(9399, 12, 2), "3"
    );

    @Autowired
    FileDao fileDao;

    @Test
    public void getAllFilesByUserIdTest() {
        List<File> files = fileDao.getAllFilesByUserId(1L);
        assertTrue(files.size() > 0);
    }

    @Test
    public void getAllFilesTest() {
        List<File> files = fileDao.getAllFiles();
        assertTrue(files.size() > 0);
    }

    @Test
    public void getFileByIdTest() {
        File file = fileDao.getFileById(1L);
        assertNotNull(file);
        assertEquals(ID_1, file.getId());
    }

    @Test
    public void addFileTest() {
        List<File> files = fileDao.getAllFiles();
        assertNotNull(files);
        int quantityBefore = files.size();

        Long id = fileDao.addFile(file_1);
        assertNotNull(id);

        File newFile = fileDao.getFileById(id);
        assertNotNull(newFile);
        assertEquals(file_1.getId(), newFile.getId());
        assertEquals(file_1.getUser_id(), newFile.getUser_id());
        assertEquals(file_1.getUrl(), newFile.getUrl());
        assertEquals(file_1.getDescription(), newFile.getDescription());
        assertEquals(file_1.getDate(), newFile.getDate());
        assertEquals(file_1.getCategory(), newFile.getCategory());

        files = fileDao.getAllFiles();
        assertNotNull(files);
        assertEquals(quantityBefore + 1, files.size());
    }



    @Test
    public void updateFileTest() {
        File file = fileDao.getFileById(2L);
        assertNotNull(file);

        file.setDescription("updatedDescription");
        file.setCategory("3");

        int count = fileDao.updateFile(file);
        assertEquals(1, count);

        File updatedFile = fileDao.getFileById(file.getId());
        assertEquals(file.getId(), updatedFile.getId());
        assertEquals(file.getUser_id(), updatedFile.getUser_id());
        assertEquals(file.getUrl(), updatedFile.getUrl());
        assertEquals(file.getDescription(), updatedFile.getDescription());
        assertEquals(file.getDate(), updatedFile.getDate());
        assertEquals(file.getCategory(), updatedFile.getCategory());
    }

    @Test
    public void deleteFileTest() {
        Long id =  fileDao.addFile(file_2);
        assertNotNull(id);

        List<File> files = fileDao.getAllFiles();
        assertNotNull(files);
        int quantityBefore = files.size();

        int count = fileDao.deleteFile(id);
        assertEquals(1, count);

        files = fileDao.getAllFiles();
        assertNotNull(files);
        assertEquals(quantityBefore - 1, files.size());
    }
}
