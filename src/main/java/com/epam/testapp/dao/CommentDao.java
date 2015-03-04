package com.epam.testapp.dao;

import com.epam.testapp.model.CommentTO;

import java.util.List;

public interface CommentDao extends CommonDao<CommentTO,Long> {
    List<CommentTO> findAll(Long newsId);
    void save(List<CommentTO> comments);
    void deleteByNewsId(List<Long> newsIds);
}
