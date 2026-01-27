package org.exercises.urlshortener;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class UrlShortenerService {

    private final UrlShortenerRepository repository;
    private final SecureRandom random = new SecureRandom();
    private final String alphabet;

    public UrlShortenerService(UrlShortenerRepository repository, UrlShortenerProperties properties) {
        this.repository = repository;
        this.alphabet = properties.getAlphabet();
    }

    public UrlEntity findByUrlCode(String urlCode) throws UnknownURLCodeException, ExpiredURLException {
        UrlEntity entity = repository.findById(urlCode).orElseThrow(UnknownURLCodeException::new);

        if (entity.isExpired()) {
            throw new ExpiredURLException();
        }

        return entity;
    }

    public UrlEntity saveUrl(String url) {
        String code = generateCode();

        while (repository.existsById(code)) {
            code = generateCode();
        }

        return repository.save(new UrlEntity(code, url, LocalDateTime.now().plusSeconds(30)));
    }

    private String generateCode() {
        int length = random.nextInt(5, 11);
        StringBuilder code = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            code.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }

        return code.toString();
    }

}
