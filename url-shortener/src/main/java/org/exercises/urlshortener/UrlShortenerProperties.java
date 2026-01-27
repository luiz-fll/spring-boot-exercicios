package org.exercises.urlshortener;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "shortener.service")
public class UrlShortenerProperties {

    private String baseurl;
    private String alphabet;

    public String getBaseUrl() {
        return baseurl;
    }

    public void setBaseUrl(String baseurl) {
        this.baseurl = baseurl;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

}
