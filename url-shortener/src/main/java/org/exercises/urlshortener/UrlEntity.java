package org.exercises.urlshortener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "url_table")
public class UrlEntity {

    @Id
    @Column(length = 10, nullable = false)
    private String code;

    @Column(length = 2048, nullable = false)
    private String url;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    protected UrlEntity() {}

    public UrlEntity(String code, String url, LocalDateTime expiresAt) {
        this.code = code;
        this.url = url;
        this.expiresAt = expiresAt;
    }

    public String getCode() {
        return code;
    }

    public String getUrl() {
        return url;
    }

    public boolean isExpired() {
        return expiresAt != null && expiresAt.isBefore(LocalDateTime.now());
    }

}
