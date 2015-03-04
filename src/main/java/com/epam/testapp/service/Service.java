package com.epam.testapp.service;

import com.epam.testapp.dao.NewsDao;
import com.epam.testapp.form.NewsManagerForm;

@org.springframework.stereotype.Service
public interface Service {

    public void getNewsList(NewsManagerForm form);
    public void saveNews(NewsManagerForm form);
    public void deleteNews(NewsManagerForm form);
    public void viewNews(NewsManagerForm form);
    public void addAuthor(NewsManagerForm form);
    public void searchNewsByAuthor(NewsManagerForm form);
    public void addTags(NewsManagerForm form);
    public void searchNewsByTags(NewsManagerForm form);
    public void addComments(NewsManagerForm form);
    public void deleteComment(NewsManagerForm form);
    public void deleteCommentsOfNews(NewsManagerForm form);
    public void setNewsDao(NewsDao dao);
}
