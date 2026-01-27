package org.exercises.urlshortener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "url_table")
public class UrlEntity {

    @Id
    @Column(length = 10, nullable = false)
    private String code;

    @Column(length = 2048, nullable = false)
    private String url;

    protected UrlEntity() {}

    public UrlEntity(String code, String url) {
        this.code = code;
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }

}
