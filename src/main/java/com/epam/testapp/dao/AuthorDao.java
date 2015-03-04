package com.epam.testapp.dao;

import com.epam.testapp.model.AuthorTO;

import java.util.List;

public interface AuthorDao extends CommonDao<AuthorTO, Long> {
    List<AuthorTO> findAll(Long newsId);
    void save(List<AuthorTO> authors);
    void attachToNews(Long newsId, List<Long> authorIds);
}
