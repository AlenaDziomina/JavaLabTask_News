package com.epam.testapp.dao.impl;

import com.epam.testapp.DBUnitImplTest;
import com.epam.testapp.model.AuthorTO;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class AuthorDBUnitImplTest extends DBUnitImplTest {

    public IDataSet getDataSet() throws Exception {
        File file = new File("src/test/resources/data/author.xml");
        return new FlatXmlDataSetBuilder().build(new FileInputStream(file));
    }

    @Test
    public void testFindAll() throws Exception {
        List<AuthorTO> authors = authorDao.findAll();
        assertEquals(3, authors.size());
    }

    @Test
    public void testFindAll1() throws Exception {
        Long id = new Long(5);
        List<AuthorTO> authors = authorDao.findAll(id);
        assertEquals(2, authors.size());

    }

    @Test
    public void testFindById() throws Exception {
        Long id = new Long(1);
        AuthorTO author = authorDao.findById(id);
        assertEquals("ALENA", author.getName());
    }

    @Test
    public void testSave() throws Exception {
        AuthorTO author = new AuthorTO();
        author.setName("TEST_NAME");
        authorDao.save(author);
        List<AuthorTO> list = authorDao.findAll();
        assertEquals(4, list.size());
    }

    @Test
    public void testSave1() throws Exception {
        AuthorTO author1 = new AuthorTO();
        author1.setName("TEST_NAME1");
        AuthorTO author2 = new AuthorTO();
        author2.setName("TEST_NAME2");
        List<AuthorTO> authors = new ArrayList<AuthorTO>();
        authors.add(author1);
        authors.add(author2);
        authorDao.save(authors);
        List<AuthorTO> list = authorDao.findAll();
        assertEquals(5, list.size());
    }

    @Test
    public void testUpdate() throws Exception {
        AuthorTO author = new AuthorTO();
        Long id = new Long(1);
        author.setAuthorId(id);
        author.setName("TEST_NAME1");
        authorDao.update(author);
        AuthorTO actual = authorDao.findById(id);
        assertEquals(author.getName(), actual.getName());
    }

    @Test
    public void testDelete() throws Exception {
        final List<Long> ids = new ArrayList<Long>();
        ids.add(new Long(3));
        authorDao.delete(ids);

        List<AuthorTO> list = authorDao.findAll();
        assertEquals(2, list.size());
    }

    @Test
    public void testAttachToNews() throws Exception {
        final List<Long> ids = new ArrayList<Long>();
        ids.add(new Long(2));
        ids.add(new Long(3));
        final Long newsId = new Long(2);
        authorDao.attachToNews(newsId, ids);

        List<AuthorTO> list = authorDao.findAll(newsId);
        assertEquals(2, list.size());
    }
}