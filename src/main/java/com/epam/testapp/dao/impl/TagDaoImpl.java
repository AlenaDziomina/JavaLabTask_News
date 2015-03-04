package com.epam.testapp.dao.impl;

import com.epam.testapp.dao.TagDao;
import com.epam.testapp.model.TagTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class TagDaoImpl implements TagDao {

    private static final String FIND_ALL = "SELECT * FROM TAGS";
    private static final String FIND_BY_ID = "SELECT * FROM TAGS WHERE TAG_ID=?";
    private static final String FIND_ALL_BY_NEWSID = "SELECT * FROM TAGS t1 " +
            "JOIN NEWS_TAG t2 ON t2.TAG_ID = t1.TAG_ID" +
            " WHERE t2.NEWS_ID=?";

    private static final String SAVE_TAG = "INSERT INTO TAGS (TAG_ID, TAG_NAME) VALUES (TAGS_SEQ.nextval, ?)";

    private static final String UPDATE_TAG = "UPDATE TAGS SET TAG_NAME=? WHERE TAG_ID=?";
    private static final String DELETE_BY_ID = "DELETE FROM TAGS WHERE TAG_ID=?";

    private static final String ATTACH_TO_NEWS = "INSERT INTO NEWS_TAG (NEWS_TAG_ID, NEWS_ID, TAG_ID) " +
            "VALUES (NEWS_TAG_SEQ.NEXTVAL, ?, ?)";

    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate template;

    @Override
    public List<TagTO> findAll() {
        return template.query(FIND_ALL, new TagMapper());
    }

    @Override
    public List<TagTO> findAll(Long newsId) {
        return template.query(FIND_ALL_BY_NEWSID, new Object[]{newsId}, new TagMapper());
    }

    @Override
    public TagTO findById(Long entityId) {
        return template.queryForObject(FIND_BY_ID, new Object[]{entityId}, new TagMapper());
    }

    @Override
    public Long save(TagTO tag) {
        int[] types = new int[] {Types.VARCHAR};
        Object[] params = new Object[] {tag.getTagName()};
        Long row = Long.valueOf(template.update(SAVE_TAG, params, types));
        return row;
    }

    @Override
    public void save(final List<TagTO> tags) {
        template.batchUpdate(SAVE_TAG, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TagTO tag = tags.get(i);
                ps.setString(1, tag.getTagName());
            }

            @Override
            public int getBatchSize() {
                return tags.size();
            }
        });
    }

    @Override
    public void update(TagTO tag) {
        int[] types = new int[] {Types.VARCHAR, Types.BIGINT};
        Object[] params = new Object[] {tag.getTagName(), tag.getTagId()};
        Long row = Long.valueOf(template.update(UPDATE_TAG, params, types));
    }

    @Override
    public void delete(final List<Long> ids) {
        template.batchUpdate(DELETE_BY_ID, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, ids.get(i));
            }

            @Override
            public int getBatchSize() {
                return ids.size();
            }
        });
    }

    @Override
    public void attachToNews(final Long newsId, final List<Long> tagIds) {
        template.batchUpdate(ATTACH_TO_NEWS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, newsId);
                ps.setLong(2, tagIds.get(i));
            }

            @Override
            public int getBatchSize() {
                return tagIds.size();
            }
        });
    }

    private class TagMapper implements RowMapper<TagTO> {
        @Override
        public TagTO mapRow(ResultSet rs, int i) throws SQLException {
            TagTO tag = new TagTO();
            tag.setTagId(rs.getLong("TAG_ID"));
            tag.setTagName(rs.getString("TAG_NAME"));
            return tag;
        }
    }
}
