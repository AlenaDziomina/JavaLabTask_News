package com.epam.testapp.dao.impl;

import com.epam.testapp.DBUnitImplTest;
import com.epam.testapp.model.NewsTO;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class NewsDBUnitImplTest extends DBUnitImplTest {

    public IDataSet getDataSet() throws Exception {
        File file = new File("src/test/resources/data/news.xml");
        return new FlatXmlDataSetBuilder().build(new FileInputStream(file));
    }

    @Test
    public void testFindAll() throws Exception {
        List<NewsTO> news = newsDao.findAll();
        assertEquals(5, news.size());
    }

    @Test
    public void testFindById() throws Exception {
        Long id = new Long(1);
        NewsTO news = newsDao.findById(id);
        assertEquals(id, news.getNewsId());
        assertEquals("TITLE", news.getTitle());
        assertEquals("S", news.getShortText());
        assertEquals("F", news.getFullText());
        assertEquals("1997-01-31 09:26:50.12", news.getCreationDate().toString());
        assertEquals("2015-02-17", news.getModificationDate().toString());
    }


    @Test
    public void testFindByAuthor() throws Exception {
        Long id = new Long(1);
        Long id2 = new Long(5);
        NewsTO news = newsDao.findByAuthor(id);
        assertEquals(id2, news.getNewsId());
    }

    @Test
    public void testFindByTag() throws Exception {
        Long id = new Long(1);
        Long id2 = new Long(5);
        NewsTO news = newsDao.findByTag(id);
        assertEquals(id2, news.getNewsId());
    }

    @Test
    public void testSave() throws Exception {
        NewsTO news = new NewsTO();
        news.setTitle("TITLE_TEST");
        news.setShortText("SHORT_TEXT_TEST");
        news.setFullText("FULL_TEXT_TEST");
        news.setCreationDate(new Timestamp(new java.util.Date().getTime()));
        news.setModificationDate(new Date(new java.util.Date().getTime()));
        newsDao.save(news);
        List<NewsTO> list = newsDao.findAll();
        assertEquals(6, list.size());
    }

    @Test
    public void testUpdate() throws Exception {
        NewsTO expectedNews = new NewsTO();
        Long id = new Long(1);
        expectedNews.setNewsId(id);
        expectedNews.setTitle("TITLE_TEST");
        expectedNews.setShortText("SHORT_TEXT_TEST");
        expectedNews.setFullText("FULL_TEXT_TEST");
        Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
        expectedNews.setCreationDate(timestamp);
        Date date = new Date(new java.util.Date().getTime());
        expectedNews.setModificationDate(date);

        newsDao.update(expectedNews);
        NewsTO actualsNews = newsDao.findById(id);
        assertEquals(id, actualsNews.getNewsId());
        assertEquals(expectedNews.getTitle(), actualsNews.getTitle());
        assertEquals(expectedNews.getShortText(), actualsNews.getShortText());
        assertEquals(expectedNews.getFullText(), actualsNews.getFullText());
        assertEquals(timestamp.toString(), actualsNews.getCreationDate().toString());
        assertEquals(date.toString(), actualsNews.getModificationDate().toString());
    }

    @Test
    public void testDelete() throws Exception {
        final List<Long> ids = new ArrayList<Long>();
        ids.add(new Long(1));
        ids.add(new Long(2));
        newsDao.delete(ids);

        List<NewsTO> list = newsDao.findAll();
        assertEquals(3, list.size());

    }
}