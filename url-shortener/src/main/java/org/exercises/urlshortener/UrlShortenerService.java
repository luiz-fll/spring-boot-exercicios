package org.exercises.urlshortener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class UrlShortenerService {

    private final UrlShortenerRepository repository;
    private final SecureRandom random = new SecureRandom();
    private final String alphabet;

    public UrlShortenerService(UrlShortenerRepository repository, UrlShortenerProperties properties) {
        this.repository = repository;
        this.alphabet = properties.getAlphabet();
    }

    public UrlEntity findByUrlCode(String urlCode) throws UnknownURLCodeException {
        return repository.findById(urlCode).orElseThrow(UnknownURLCodeException::new);
    }

    public UrlEntity saveUrl(String url) {
        String code = generateCode();

        while (repository.existsById(code)) {
            code = generateCode();
        }

        return repository.save(new UrlEntity(code, url));
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
