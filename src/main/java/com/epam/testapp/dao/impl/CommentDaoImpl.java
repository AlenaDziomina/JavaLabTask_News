package com.epam.testapp.dao.impl;

import com.epam.testapp.dao.CommentDao;
import com.epam.testapp.model.CommentTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;


@Repository
public class CommentDaoImpl implements CommentDao {

    private static final String FIND_ALL = "SELECT * FROM COMMENTS";
    private static final String FIND_ALL_BY_NEWSID = "SELECT * FROM COMMENTS WHERE NEWS_ID=?";
    private static final String FIND_BY_ID = "SELECT * FROM COMMENTS WHERE COMMENT_ID=?";

    private static final String SAVE_COMMENT = "INSERT INTO COMMENTS (COMMENT_ID, COMMENT_TEXT, CREATION_DATE, " +
            " NEWS_ID) VALUES (COMMENTS_SEQ.NEXTVAL, ?, ?, ?)";

    private static final String UPDATE_COMMENT = "UPDATE COMMENTS SET COMMENT_TEXT=?, CREATION_DATE=?, NEWS_ID=?" +
            " WHERE COMMENT_ID=?";

    private static final String DELETE_BY_ID = "DELETE FROM COMMENTS WHERE COMMENT_ID=?";
    private static final String DELETE_BY_NEWS_ID = "DELETE FROM COMMENTS WHERE NEWS_ID=?";

    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate template;

    @Override
    public List<CommentTO> findAll() {
        return template.query(FIND_ALL, new CommentsMapper());
    }

    @Override
    public List<CommentTO> findAll(Long newsId) {
        return template.query(FIND_ALL_BY_NEWSID, new Object[]{newsId}, new CommentsMapper());
    }

    @Override
    public CommentTO findById(Long commentId) {
        return template.queryForObject(FIND_BY_ID, new Object[]{commentId}, new CommentsMapper());
    }

    @Override
    public Long save(CommentTO comment) {
        int[] types = new int[] {Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT};
        Object[] params = new Object[] {comment.getCommentText(), comment.getCreationDate(), comment.getNewsId()};
        Long row = Long.valueOf(template.update(SAVE_COMMENT, params, types));
        return row;
    }

    @Override
    public void save(final List<CommentTO> comments) {
        template.batchUpdate(SAVE_COMMENT, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                CommentTO comment = comments.get(i);
                ps.setString(1, comment.getCommentText());
                ps.setTimestamp(2, comment.getCreationDate());
                ps.setLong(3, comment.getNewsId());
            }

            @Override
            public int getBatchSize() {
                return comments.size();
            }
        });
    }

    @Override
    public void update(CommentTO comment) {
        int[] types = new int[] {Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT, Types.BIGINT};
        Object[] params = new Object[] {comment.getCommentText(), comment.getCreationDate(), comment.getNewsId(),
                comment.getCommentId(),};
        Long row = Long.valueOf(template.update(UPDATE_COMMENT, params, types));
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
    public void deleteByNewsId(final List<Long> newsIds) {
        template.batchUpdate(DELETE_BY_NEWS_ID, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, newsIds.get(i));
            }

            @Override
            public int getBatchSize() {
                return newsIds.size();
            }
        });
    }

    private class CommentsMapper implements RowMapper<CommentTO> {
        @Override
        public CommentTO mapRow(ResultSet rs, int i) throws SQLException {
            CommentTO comment = new CommentTO();
            comment.setCommentId(rs.getLong("COMMENT_ID"));
            comment.setCommentText(rs.getString("COMMENT_TEXT"));
            comment.setCreationDate(rs.getTimestamp("CREATION_DATE"));
            comment.setNewsId(rs.getLong("NEWS_ID"));
            return comment;
        }
    }
}
