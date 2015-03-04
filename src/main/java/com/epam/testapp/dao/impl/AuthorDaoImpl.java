package com.epam.testapp.dao.impl;

import com.epam.testapp.dao.AuthorDao;
import com.epam.testapp.model.AuthorTO;
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
public class AuthorDaoImpl implements AuthorDao{

    private static final String FIND_ALL = "SELECT * FROM AUTHOR";
    private static final String FIND_BY_ID = "SELECT * FROM AUTHOR WHERE AUTHOR_ID=?";
    private static final String FIND_ALL_BY_NEWSID = "SELECT * FROM AUTHOR t1 " +
            "JOIN NEWS_AUTHOR t2 ON t2.AUTHOR_ID = t1.AUTHOR_ID" +
            " WHERE t2.NEWS_ID=?";
    private static final String SAVE_AUTHOR = "INSERT INTO AUTHOR (AUTHOR_ID, AUTHOR_NAME) VALUES (AUTHOR_SEQ.NEXTVAL, ?)";

    private static final String UPDATE_AUTHOR = "UPDATE AUTHOR SET AUTHOR_NAME=? WHERE AUTHOR_ID=?";
    private static final String DELETE_BY_ID = "DELETE FROM AUTHOR WHERE AUTHOR_ID=?";

    private static final String ATTACH_TO_NEWS = "INSERT INTO NEWS_AUTHOR (NEWS_AUTHOR_ID, NEWS_ID, AUTHOR_ID) " +
            "VALUES (NEWS_AUTHOR_SEQ.NEXTVAL, ?, ?)";

    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate template;

    @Override
    public List<AuthorTO> findAll() {
        return template.query(FIND_ALL, new AuthorMapper());
    }

    @Override
    public List<AuthorTO> findAll(Long newsId) {
        return template.query(FIND_ALL_BY_NEWSID, new Object[]{newsId}, new AuthorMapper());
    }

    @Override
    public AuthorTO findById(Long entityId) {
        return template.queryForObject(FIND_BY_ID, new Object[]{entityId}, new AuthorMapper());
    }

    @Override
    public Long save(AuthorTO author) {
        int[] types = new int[] {Types.VARCHAR};
        Object[] params = new Object[] {author.getName()};
        Long row = Long.valueOf(template.update(SAVE_AUTHOR, params, types));
        return row;
    }

    @Override
    public void save(final List<AuthorTO> authors) {
        template.batchUpdate(SAVE_AUTHOR, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                AuthorTO author = authors.get(i);
                ps.setString(1, author.getName());
            }

            @Override
            public int getBatchSize() {
                return authors.size();
            }
        });
    }

    @Override
    public void update(AuthorTO author) {
        int[] types = new int[] {Types.VARCHAR, Types.BIGINT};
        Object[] params = new Object[] {author.getName(), author.getAuthorId()};
        Long row = Long.valueOf(template.update(UPDATE_AUTHOR, params, types));
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
    public void attachToNews(final Long newsId, final List<Long> authorIds) {
        template.batchUpdate(ATTACH_TO_NEWS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, newsId);
                ps.setLong(2, authorIds.get(i));
            }

            @Override
            public int getBatchSize() {
                return authorIds.size();
            }
        });
    }

    private class AuthorMapper implements RowMapper<AuthorTO> {

        @Override
        public AuthorTO mapRow(ResultSet rs, int i) throws SQLException {
            AuthorTO author = new AuthorTO();
            author.setAuthorId(rs.getLong("AUTHOR_ID"));
            author.setName(rs.getString("AUTHOR_NAME"));
            return author;
        }
    }
}
