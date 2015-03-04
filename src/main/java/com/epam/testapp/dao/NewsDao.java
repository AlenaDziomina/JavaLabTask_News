package com.epam.testapp.dao;

import com.epam.testapp.model.NewsTO;

public interface NewsDao extends CommonDao<NewsTO, Long> {
    NewsTO findByAuthor(Long Author);
    NewsTO findByTag(Long tagId);

}
