package com.epam.testapp.service;

import com.epam.testapp.dao.AuthorDao;
import com.epam.testapp.dao.CommentDao;
import com.epam.testapp.dao.NewsDao;
import com.epam.testapp.dao.TagDao;
import com.epam.testapp.form.NewsManagerForm;
import com.epam.testapp.model.AuthorTO;
import com.epam.testapp.model.NewsTO;
import com.epam.testapp.model.TagTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
public class ServiceImpl implements Service {
    @Autowired
    NewsDao newsDao;
    @Autowired
    private AuthorDao authorDao;
    @Autowired
    private TagDao tagDao;
    @Autowired
    private CommentDao commentDao;

    @Override
    public void getNewsList(NewsManagerForm form){
        newsDao.findAll();
    }

    @Override
    public void saveNews(NewsManagerForm form) {
        NewsTO news  = form.getNewsTO();
        if (news.getNewsId() == null) {
            news.setNewsId(newsDao.save(news));
        } else {
            newsDao.update(news);
        }
        Long newsId = news.getNewsId();

        List<AuthorTO> authorTOList = form.getAuthorTOList();
        List<Long> authorsToAttache = new ArrayList<Long>();
        for(AuthorTO authorTO: authorTOList) {
           authorsToAttache.add(authorTO.getAuthorId());
        }
        authorDao.attachToNews(newsId, authorsToAttache);

        List<TagTO> tagsTOList = form.getTagTOList();
        List<Long> tagsToAttache = new ArrayList<Long>();
        for(TagTO tagTO: tagsTOList) {
            tagsToAttache.add(tagTO.getTagId());
        }
        tagDao.attachToNews(newsId, tagsToAttache);
    }

    @Override
    public void deleteNews(NewsManagerForm form) {
        List<Long> deletedNews = new ArrayList<Long>();
        List<NewsTO> newsList  = form.getNewsTOList();
        for(NewsTO news : newsList) {
            deletedNews.add(news.getNewsId());
        }
        newsDao.delete(deletedNews);
    }

    @Override
    public void viewNews(NewsManagerForm form) {
        Long newsId = form.getNewsTO().getNewsId();
        newsDao.findById(newsId);
        authorDao.findAll(newsId);
        tagDao.findAll(newsId);
        commentDao.findAll(newsId);
    }

    @Override
    public void addAuthor(NewsManagerForm form) {
        Long newsId = form.getNewsTO().getNewsId();
        authorDao.attachToNews(newsId, form.getSelectedIds());
    }

    @Override
    public void searchNewsByAuthor(NewsManagerForm form) {
        Long authorId = form.getSelectedId();
        newsDao.findByAuthor(authorId);
    }

    @Override
    public void addTags(NewsManagerForm form) {
        Long newsId = form.getNewsTO().getNewsId();
        tagDao.attachToNews(newsId, form.getSelectedIds());
    }

    @Override
    public void searchNewsByTags(NewsManagerForm form) {
        Long tagId = form.getSelectedId();
        newsDao.findByTag(tagId);
    }

    @Override
    public void addComments(NewsManagerForm form) {
        commentDao.save(form.getCommentTOList());
    }

    @Override
    public void deleteComment(NewsManagerForm form) {
        commentDao.delete(form.getSelectedIds());
    }

    @Override
    public void deleteCommentsOfNews(NewsManagerForm form) {
        commentDao.deleteByNewsId(form.getSelectedIds());
    }

    public void setNewsDao(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

}
