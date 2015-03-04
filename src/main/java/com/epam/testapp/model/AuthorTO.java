package com.epam.testapp.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by User on 3/1/2015.
 */
@Entity
@Table(name = "AUTHOR")
public class AuthorTO implements Serializable {
    private Long authorId;
    private String name;

    @Id
    @Column(name = "AUTHOR_ID")
    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Basic
    @Column(name = "AUTHOR_NAME", length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthorTO authorTO = (AuthorTO) o;

        if (authorId != null ? !authorId.equals(authorTO.authorId) : authorTO.authorId != null) return false;
        if (name != null ? !name.equals(authorTO.name) : authorTO.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = authorId != null ? authorId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthorTO{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                '}';
    }
}
