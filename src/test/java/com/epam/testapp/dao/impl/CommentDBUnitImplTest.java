package com.epam.testapp.dao.impl;

import com.epam.testapp.DBUnitImplTest;
import com.epam.testapp.model.CommentTO;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class CommentDBUnitImplTest extends DBUnitImplTest {

    public IDataSet getDataSet() throws Exception {
        File file = new File("src/test/resources/data/comments.xml");
        return new FlatXmlDataSetBuilder().build(new FileInputStream(file));
    }

    @Test
    public void testFindAll() throws Exception {
        List<CommentTO> comments = commentDao.findAll();
        assertEquals(3, comments.size());
    }

    @Test
    public void testFindAll1() throws Exception {
        Long id = new Long(5);
        List<CommentTO> comments = commentDao.findAll(id);
        assertEquals(2, comments.size());
    }

    @Test
    public void testFindById() throws Exception {
        Long id = new Long(1);
        CommentTO comment = commentDao.findById(id);
        assertEquals("COMMENT_TEXT1", comment.getCommentText());
        assertEquals("1997-01-31 09:26:50.12", comment.getCreationDate().toString());
    }

    @Test
    public void testSave() throws Exception {
        CommentTO comment = new CommentTO();
        comment.setCommentText("TEXT_TEST");
        comment.setCreationDate(new Timestamp(new java.util.Date().getTime()));
        Long newsId = new Long(1);
        comment.setNewsId(newsId);
        commentDao.save(comment);
        List<CommentTO> list = commentDao.findAll();
        assertEquals(4, list.size());
    }

    @Test
    public void testSave1() throws Exception {
        CommentTO comment1 = new CommentTO();
        comment1.setCommentText("TEXT_TEST1");
        comment1.setCreationDate(new Timestamp(new java.util.Date().getTime()));
        Long newsId1 = new Long(1);
        comment1.setNewsId(newsId1);

        CommentTO comment2 = new CommentTO();
        comment2.setCommentText("TEXT_TEST2");
        comment2.setCreationDate(new Timestamp(new java.util.Date().getTime()));
        Long newsId2 = new Long(2);
        comment2.setNewsId(newsId2);

        final List<CommentTO> comments = new ArrayList<CommentTO>();
        comments.add(comment1);
        comments.add(comment2);
        commentDao.save(comments);
        List<CommentTO> list = commentDao.findAll();
        assertEquals(5, list.size());
    }

    @Test
    public void testUpdate() throws Exception {
        CommentTO expectedComment = new CommentTO();
        Long id = new Long(1);
        expectedComment.setCommentId(id);
        expectedComment.setCommentText("TEXT_TEST");
        expectedComment.setCreationDate(new Timestamp(new java.util.Date().getTime()));
        Long newsId = new Long(1);
        expectedComment.setNewsId(newsId);

        commentDao.update(expectedComment);
        CommentTO actualsComment = commentDao.findById(id);
        assertEquals(id, actualsComment.getCommentId());
        assertEquals(newsId, actualsComment.getNewsId());
        assertEquals(expectedComment.getCommentText(), actualsComment.getCommentText());
        assertEquals(expectedComment.getCreationDate(), actualsComment.getCreationDate());
    }

    @Test
    public void testDelete() throws Exception {
        final List<Long> ids = new ArrayList<Long>();
        ids.add(new Long(3));
        commentDao.delete(ids);

        List<CommentTO> list = commentDao.findAll();
        assertEquals(2, list.size());
    }

    @Test
    public void testDeleteByNewsId() throws Exception {
        final List<Long> ids = new ArrayList<Long>();
        ids.add(new Long(5));
        commentDao.deleteByNewsId(ids);

        List<CommentTO> list = commentDao.findAll();
        assertEquals(1, list.size());
    }

}