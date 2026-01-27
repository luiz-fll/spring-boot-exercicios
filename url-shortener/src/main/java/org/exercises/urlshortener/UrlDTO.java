package org.exercises.urlshortener;

public record UrlDTO(String url) {

    public static UrlDTO from(UrlEntity entity, String baseUrl) {
        return new UrlDTO(baseUrl + entity.getCode());
    }

}
