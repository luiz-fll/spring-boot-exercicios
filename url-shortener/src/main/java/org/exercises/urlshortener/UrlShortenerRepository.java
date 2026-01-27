package org.exercises.urlshortener;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlShortenerRepository extends JpaRepository<UrlEntity, String> {}
