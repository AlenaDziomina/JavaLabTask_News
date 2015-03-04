package com.epam.testapp.dao.impl;

import com.epam.testapp.DBUnitImplTest;
import com.epam.testapp.model.TagTO;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class TagDBUnitImplTest extends DBUnitImplTest {

    public IDataSet getDataSet() throws Exception {
        File file = new File("src/test/resources/data/tags.xml");
        return new FlatXmlDataSetBuilder().build(new FileInputStream(file));
    }

    @Test
    public void testFindAll() throws Exception {
        List<TagTO> tags = tagDao.findAll();
        assertEquals(3, tags.size());
    }

    @Test
    public void testFindAll1() throws Exception {
        Long id = new Long(5);
        List<TagTO> tags = tagDao.findAll(id);
        assertEquals(2, tags.size());
    }

    @Test
    public void testFindById() throws Exception {
        Long id = new Long(1);
        TagTO tags = tagDao.findById(id);
        assertEquals("USA", tags.getTagName());
    }

    @Test
    public void testSave() throws Exception {
        TagTO tags = new TagTO();
        tags.setTagName("TEST_NAME");
        tagDao.save(tags);
        List<TagTO> list = tagDao.findAll();
        assertEquals(4, list.size());
    }

    @Test
    public void testSave1() throws Exception {
        TagTO tags1 = new TagTO();
        tags1.setTagName("TEST_NAME1");
        TagTO tags2 = new TagTO();
        tags2.setTagName("TEST_NAME2");
        List<TagTO> tags = new ArrayList<TagTO>();
        tags.add(tags1);
        tags.add(tags2);
        tagDao.save(tags);
        List<TagTO> list = tagDao.findAll();
        assertEquals(5, list.size());}

    @Test
    public void testUpdate() throws Exception {
        TagTO tag = new TagTO();
        Long id = new Long(1);
        tag.setTagId(id);
        tag.setTagName("TEST_NAME1");
        tagDao.update(tag);
        TagTO actual = tagDao.findById(id);
        assertEquals(tag.getTagName(), actual.getTagName());
    }

    @Test
    public void testDelete() throws Exception {
        final List<Long> ids = new ArrayList<Long>();
        ids.add(new Long(3));
        tagDao.delete(ids);

        List<TagTO> list = tagDao.findAll();
        assertEquals(2, list.size());
    }

    @Test
    public void testAttachToNews() throws Exception {
        final List<Long> ids = new ArrayList<Long>();
        ids.add(new Long(2));
        ids.add(new Long(3));
        final Long newsId = new Long(2);
        tagDao.attachToNews(newsId, ids);

        List<TagTO> list = tagDao.findAll(newsId);
        assertEquals(2, list.size());
    }
}