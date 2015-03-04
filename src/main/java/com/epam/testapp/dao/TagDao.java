package com.epam.testapp.dao;

import com.epam.testapp.model.TagTO;

import java.util.List;

public interface TagDao extends CommonDao<TagTO, Long> {
    List<TagTO> findAll(Long newsId);
    void save(List<TagTO> tags);
    void attachToNews(final Long newsId, List<Long> tagIds);
}
