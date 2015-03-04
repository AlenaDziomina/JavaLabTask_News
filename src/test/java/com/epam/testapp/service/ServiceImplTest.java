package com.epam.testapp.service;

import com.epam.testapp.DBUnitImplTest;
import com.epam.testapp.dao.NewsDao;
import com.epam.testapp.dao.impl.NewsDaoImpl;
import com.epam.testapp.form.NewsManagerForm;
import com.epam.testapp.model.AuthorTO;
import com.epam.testapp.model.CommentTO;
import com.epam.testapp.model.NewsTO;
import com.epam.testapp.model.TagTO;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ServiceImplTest extends DBUnitImplTest {

    private static NewsTO newsForSave;
    private static NewsTO newsForUpdate;
    private static AuthorTO authorForSave;
    private static TagTO tagForSave;
    private static CommentTO commentForSave;
    private static List<NewsTO> newsForDelete;
    private static List<AuthorTO> authorsForSave;
    private static List<TagTO> tagsForSave;
    private static List<CommentTO> commentsForSave;
    private static final Long id = new Long(1);
    private static final List<Long> ids = new LinkedList<Long>();

    static {
        ids.add(id);
        newsForSave = new NewsTO();
        newsForSave.setTitle("TITLE_TEST");
        newsForSave.setShortText("SHORT_TEXT_TEST");
        newsForSave.setFullText("FULL_TEXT_TEST");
        newsForSave.setCreationDate(new Timestamp(new java.util.Date().getTime()));
        newsForSave.setModificationDate(new Date(new java.util.Date().getTime()));

        newsForUpdate = new NewsTO();
        newsForUpdate.setNewsId(id);
        newsForUpdate.setTitle("TITLE_TEST");
        newsForUpdate.setShortText("SHORT_TEXT_TEST");
        newsForUpdate.setFullText("FULL_TEXT_TEST");
        newsForUpdate.setCreationDate(new Timestamp(new java.util.Date().getTime()));
        newsForUpdate.setModificationDate(new Date(new java.util.Date().getTime()));

        newsForDelete = new LinkedList<NewsTO>();
        newsForDelete.add(newsForUpdate);
        newsForDelete.add(newsForUpdate);


        authorsForSave = new LinkedList<AuthorTO>();
        authorForSave = new AuthorTO();
        authorForSave.setAuthorId(id);
        authorForSave.setName("TEST_NAME");
        authorsForSave.add(authorForSave);
        authorsForSave.add(authorForSave);

        tagsForSave = new LinkedList<TagTO>();
        tagForSave = new TagTO();
        tagForSave.setTagId(id);
        tagForSave.setTagName("TEST_NAME");
        tagsForSave.add(tagForSave);
        tagsForSave.add(tagForSave);

        commentsForSave = new LinkedList<CommentTO>();
        commentForSave = new CommentTO();
        commentForSave.setCommentText("TEST_TEXT");
        commentForSave.setCreationDate(new Timestamp(new java.util.Date().getTime()));
        commentForSave.setNewsId(id);
        commentsForSave.add(commentForSave);
        commentsForSave.add(commentForSave);
    }


    public IDataSet getDataSet() throws Exception {
        File file = new File("src/test/resources/data/service.xml");
        return new FlatXmlDataSetBuilder().build(new FileInputStream(file));
    }

    @Test
    public void testGetNewsList() throws Exception {
        NewsDao newsDao = Mockito.mock(NewsDaoImpl.class);
        service.setNewsDao(newsDao);
        List<NewsTO> newsList = new LinkedList<NewsTO>();
        when(newsDao.findAll()).thenReturn(newsList);
        NewsManagerForm form = new NewsManagerForm();
        service.getNewsList(form);
        verify(newsDao).findAll();
    }

    @Test
    public void testSaveNews() throws Exception {
        NewsManagerForm form = Mockito.mock(NewsManagerForm.class);
        when(form.getNewsTO()).thenReturn(newsForSave);
        when(form.getAuthorTOList()).thenReturn(authorsForSave);
        when(form.getTagTOList()).thenReturn(tagsForSave);
        service.saveNews(form);
        verify(form).getNewsTO();
        verify(form).getAuthorTOList();
        verify(form).getTagTOList();
    }

    @Test
    public void testDeleteNews() throws Exception {
        NewsManagerForm form = Mockito.mock(NewsManagerForm.class);
        when(form.getNewsTOList()).thenReturn(newsForDelete);
        service.deleteNews(form);
        verify(form).getNewsTOList();
    }

    @Test
    public void testViewNews() throws Exception {
        NewsManagerForm form = Mockito.mock(NewsManagerForm.class);
        when(form.getNewsTO()).thenReturn(newsForUpdate);
        service.viewNews(form);
        verify(form).getNewsTO();
    }

    @Test
    public void testAddAuthor() throws Exception {
        NewsManagerForm form = Mockito.mock(NewsManagerForm.class);
        when(form.getNewsTO()).thenReturn(newsForUpdate);
        when(form.getSelectedIds()).thenReturn(ids);
        service.addAuthor(form);
        verify(form).getNewsTO();
        verify(form).getSelectedIds();
    }

    @Test
    public void testSearchNewsByAuthor() throws Exception {
        NewsManagerForm form = Mockito.mock(NewsManagerForm.class);
        when(form.getSelectedId()).thenReturn(id);
        service.searchNewsByAuthor(form);
        verify(form).getSelectedId();
    }

    @Test
    public void testAddTags() throws Exception {
        NewsManagerForm form = Mockito.mock(NewsManagerForm.class);
        when(form.getNewsTO()).thenReturn(newsForUpdate);
        when(form.getSelectedIds()).thenReturn(ids);
        service.addTags(form);
        verify(form).getNewsTO();
        verify(form).getSelectedIds();
    }

    @Test
    public void testSearchNewsByTags() throws Exception {
        NewsManagerForm form = Mockito.mock(NewsManagerForm.class);
        when(form.getSelectedId()).thenReturn(id);
        service.searchNewsByTags(form);
        verify(form).getSelectedId();
    }

    @Test
    public void testAddComments() throws Exception {
        NewsManagerForm form = Mockito.mock(NewsManagerForm.class);
        when(form.getCommentTOList()).thenReturn(commentsForSave);
        service.addComments(form);
        verify(form).getCommentTOList();
    }

    @Test
    public void testDeleteComment() throws Exception {
        NewsManagerForm form = Mockito.mock(NewsManagerForm.class);
        when(form.getSelectedIds()).thenReturn(ids);
        service.deleteComment(form);
        verify(form).getSelectedIds();
    }

    @Test
    public void testDeleteCommentsOfNews() throws Exception {
        NewsManagerForm form = Mockito.mock(NewsManagerForm.class);
        when(form.getSelectedIds()).thenReturn(ids);
        service.deleteCommentsOfNews(form);
        verify(form).getSelectedIds();
    }

}