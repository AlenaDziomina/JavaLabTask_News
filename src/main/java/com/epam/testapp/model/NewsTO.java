package com.epam.testapp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;

@Entity
@Table (name = "NEWS2")
public class NewsTO implements Serializable {
    private Long newsId;
    private String title;
    private String shortText;
    private String fullText;
    private Timestamp creationDate;
    private Date modificationDate;

    @Id
    @Column(name = "NEWS_ID")
    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    @Basic
    @Column(name = "TITLE", length = 30)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "SHORT_TEXT", length = 100)
    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    @Basic
    @Column(name = "FULL_TEXT", length = 2000)
    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
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
    @Column(name = "MODIFICATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsTO newsTO = (NewsTO) o;

        if (creationDate != null ? !creationDate.equals(newsTO.creationDate) : newsTO.creationDate != null)
            return false;
        if (fullText != null ? !fullText.equals(newsTO.fullText) : newsTO.fullText != null) return false;
        if (modificationDate != null ? !modificationDate.equals(newsTO.modificationDate) : newsTO.modificationDate != null)
            return false;
        if (newsId != null ? !newsId.equals(newsTO.newsId) : newsTO.newsId != null) return false;
        if (shortText != null ? !shortText.equals(newsTO.shortText) : newsTO.shortText != null) return false;
        if (title != null ? !title.equals(newsTO.title) : newsTO.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = newsId != null ? newsId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (shortText != null ? shortText.hashCode() : 0);
        result = 31 * result + (fullText != null ? fullText.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (modificationDate != null ? modificationDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewsTO{" +
                "newsId=" + newsId +
                ", title='" + title + '\'' +
                ", shortText='" + shortText + '\'' +
                ", fullText='" + fullText + '\'' +
                ", creationDate=" + creationDate +
                ", modificationDate=" + modificationDate +
                '}';
    }
}
