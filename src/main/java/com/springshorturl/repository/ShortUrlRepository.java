package com.springshorturl.repository;

import com.springshorturl.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByUrl(String shortCode);
    boolean existsByUrl(String shortCode);
}
