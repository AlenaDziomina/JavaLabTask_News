package com.epam.testapp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "COMMENTS")
public class CommentTO implements Serializable {
    private Long commentId;
    private String commentText;
    private Timestamp creationDate;
    private Long newsId;

    @Id
    @Column(name = "COMMENT_ID")
    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Basic
    @Column(name = "COMMENT_TEXT", length = 100)
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @Basic
    @Column(name = "CREATION_DATE")
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "NEWS_ID")
    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentTO that = (CommentTO) o;

        if (commentId != null ? !commentId.equals(that.commentId) : that.commentId != null) return false;
        if (commentText != null ? !commentText.equals(that.commentText) : that.commentText != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = commentId != null ? commentId.hashCode() : 0;
        result = 31 * result + (commentText != null ? commentText.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommentTO{" +
                "commentId=" + commentId +
                ", commentText='" + commentText + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
