package com.epam.testapp.form;

import com.epam.testapp.model.AuthorTO;
import com.epam.testapp.model.CommentTO;
import com.epam.testapp.model.NewsTO;
import com.epam.testapp.model.TagTO;

import java.util.List;

public class NewsManagerForm {

    private NewsTO newsTO;
    private TagTO tagTO;
    private AuthorTO authorTO;
    private CommentTO commentTO;
    List<Long> selectedIds;
    Long selectedId;
    private List<NewsTO> newsTOList;
    private List<TagTO> tagTOList;
    private List<AuthorTO> authorTOList;
    private List<CommentTO> commentTOList;
    public List<Long> getSelectedIds() {
        return selectedIds;
    }


    public Long getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(Long selectedId) {
        this.selectedId = selectedId;
    }

    public void setSelectedIds(List<Long> selectedIds) {
        this.selectedIds = selectedIds;
    }

    public NewsTO getNewsTO() {
        return newsTO;
    }

    public void setNewsTO(NewsTO newsTO) {
        this.newsTO = newsTO;
    }

    public TagTO getTagTO() {
        return tagTO;
    }

    public void setTagTO(TagTO tagTO) {
        this.tagTO = tagTO;
    }

    public CommentTO getCommentTO() {
        return commentTO;
    }

    public void setCommentTO(CommentTO commentTO) {
        this.commentTO = commentTO;
    }

    public List<NewsTO> getNewsTOList() {
        return newsTOList;
    }

    public void setNewsTOList(List<NewsTO> newsTOList) {
        this.newsTOList = newsTOList;
    }

    public List<TagTO> getTagTOList() {
        return tagTOList;
    }

    public void setTagTOList(List<TagTO> tagTOList) {
        this.tagTOList = tagTOList;
    }

    public List<AuthorTO> getAuthorTOList() {
        return authorTOList;
    }

    public void setAuthorTOList(List<AuthorTO> authorTOList) {
        this.authorTOList = authorTOList;
    }

    public List<CommentTO> getCommentTOList() {
        return commentTOList;
    }

    public void setCommentTOList(List<CommentTO> commentTOList) {
        this.commentTOList = commentTOList;
    }

    public AuthorTO getAuthorTO() {
        return authorTO;
    }

    public void setAuthorTO(AuthorTO authorTO) {
        this.authorTO = authorTO;
    }
}
