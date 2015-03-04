package com.epam.testapp.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TAG")
public class TagTO implements Serializable {
    private Long tagId;
    private String tagName;

    @Id
    @Column(name = "TAG_ID")
    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @Basic
    @Column(name = "TAG_NAME", length = 30)
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagTO tagTO = (TagTO) o;

        if (tagId != null ? !tagId.equals(tagTO.tagId) : tagTO.tagId != null) return false;
        if (tagName != null ? !tagName.equals(tagTO.tagName) : tagTO.tagName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tagId != null ? tagId.hashCode() : 0;
        result = 31 * result + (tagName != null ? tagName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TagTO{" +
                "tagId=" + tagId +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
