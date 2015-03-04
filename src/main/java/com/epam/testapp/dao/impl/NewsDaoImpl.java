package com.epam.testapp.dao.impl;

import com.epam.testapp.dao.NewsDao;
import com.epam.testapp.model.NewsTO;
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
public class NewsDaoImpl implements NewsDao {

    private static final String FIND_ALL = "SELECT * FROM NEWS2 n " +
            "LEFT JOIN (SELECT NEWS_ID, COUNT(*) cnt FROM COMMENTS GROUP BY NEWS_ID) c ON n.NEWS_ID = c.NEWS_ID " +
            "ORDER BY CNT DESC NULLS LAST";
    private static final String FIND_BY_ID = "SELECT * FROM NEWS2 WHERE NEWS_ID=?";
    private static final String FIND_BY_AUTHOR = "SELECT * FROM NEWS2 n " +
            "INNER JOIN NEWS_AUTHOR a ON a.NEWS_ID = n.NEWS_ID " +
            "WHERE a.AUTHOR_ID=?";
    private static final String FIND_BY_TAG = "SELECT * FROM NEWS2 n " +
            "INNER JOIN NEWS_TAG t ON t.NEWS_ID = n.NEWS_ID " +
            "WHERE t.TAG_ID=?";

    private static final String SAVE_NEWS = "INSERT INTO NEWS2 (NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, " +
            "CREATION_DATE, MODIFICATION_DATE) VALUES (NEWS2_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";

    private static final String UPDATE_NEWS = "UPDATE NEWS2 SET TITLE=?, SHORT_TEXT=?, FULL_TEXT=?," +
            "CREATION_DATE=?, MODIFICATION_DATE=? WHERE NEWS_ID=?";

    private static final String DELETE_NEWS_BY_ID = "DELETE FROM NEWS2 WHERE NEWS_ID=?";
    private static final String DELETE_NEWS_TAG = "DELETE FROM NEWS_TAG WHERE NEWS_ID=?";
    private static final String DELETE_NEWS_AUTHOR = "DELETE FROM NEWS_AUTHOR WHERE NEWS_ID=?";

    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate template;

    @Override
    public List<NewsTO> findAll() {
        return template.query(FIND_ALL, new NewsMapper());
    }

    @Override
    public NewsTO findById(Long entityId) {
        return template.queryForObject(FIND_BY_ID, new Object[]{entityId}, new NewsMapper());
    }

    @Override
    public NewsTO findByAuthor(Long authorId) {
        return template.queryForObject(FIND_BY_AUTHOR, new Object[]{authorId}, new NewsMapper());
    }

    @Override
    public NewsTO findByTag(Long tagId) {
        return template.queryForObject(FIND_BY_TAG, new Object[]{tagId}, new NewsMapper());
    }

    @Override
    public Long save(NewsTO news) {
        int[] types = new int[] {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.DATE };
        Object[] params = new Object[] {news.getTitle(), news.getShortText(), news.getFullText(),
                news.getCreationDate(), news.getModificationDate()};
        Long row = Long.valueOf(template.update(SAVE_NEWS, params, types));
        return row;
    }

    @Override
    public void update(NewsTO news) {
        int[] types = new int[] {Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.DATE,
                Types.BIGINT };
        Object[] params = new Object[] {news.getTitle(), news.getShortText(), news.getFullText(),
                news.getCreationDate(), news.getModificationDate(), news.getNewsId()};
        Long row = Long.valueOf(template.update(UPDATE_NEWS, params, types));
    }

    @Override
    public void delete(final List<Long> ids) {
        template.batchUpdate(DELETE_NEWS_TAG, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, ids.get(i));
            }

            @Override
            public int getBatchSize() {
                return ids.size();
            }
        });

        template.batchUpdate(DELETE_NEWS_AUTHOR, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, ids.get(i));
            }

            @Override
            public int getBatchSize() {
                return ids.size();
            }
        });

        template.batchUpdate(DELETE_NEWS_BY_ID, new BatchPreparedStatementSetter() {
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


    private class NewsMapper implements RowMapper<NewsTO> {

        @Override
        public NewsTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            NewsTO news = new NewsTO();
            news.setNewsId(rs.getLong("NEWS_ID"));
            news.setTitle(rs.getString("TITLE"));
            news.setShortText(rs.getString("SHORT_TEXT"));
            news.setFullText(rs.getString("FULL_TEXT"));
            news.setCreationDate(rs.getTimestamp("CREATION_DATE"));
            news.setModificationDate(rs.getDate("MODIFICATION_DATE"));
            return news;
        }
    }

}
