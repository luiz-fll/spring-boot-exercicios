package org.exercises.urlshortener;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlShortenerController {

    private final UrlShortenerService service;
    private final String baseUrl;

    public UrlShortenerController(UrlShortenerService service, UrlShortenerProperties properties) {
        this.service = service;
        this.baseUrl = properties.getBaseUrl();
    }

    @PostMapping("/shorten-url")
    public UrlDTO shortenUrl(@RequestBody UrlDTO urlDTO) {
        return UrlDTO.from(service.saveUrl(urlDTO.url()), baseUrl);
    }

    @GetMapping("/{urlCode}")
    public ResponseEntity<Void> redirectToUrl(@PathVariable String urlCode) {
        String url = service.findByUrlCode(urlCode).getUrl();

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, url)
                .build();
    }

}
