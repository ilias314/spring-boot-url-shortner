package com.springshorturl.service;

import com.springshorturl.dto.CreateShortUrlResponse;
import com.springshorturl.entity.ShortUrl;
import com.springshorturl.repository.ShortUrlRepository;
import com.springshorturl.util.ShortCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import com.springshorturl.dto.GetShortUrlMetadataResponse;
import com.springshorturl.dto.GetShortUrlAnalyticsResponse;
import java.util.Optional;


import java.security.SecureRandom;

@Service
public class ShortUrlService {

    @Autowired
    private ShortUrlRepository repository;

    @Autowired
    private ShortCodeGenerator shortCodeGenerator;

    public CreateShortUrlResponse createShortUrl(String originalUrl) {
        String shortCode = shortCodeGenerator.generateShortCode();
        ShortUrl entity = new ShortUrl();
        entity.setOriginalUrl(originalUrl);
        entity.setShortCode(shortCode);
        entity.setCreatedAt(LocalDateTime.now());
        repository.save(entity);
        String shortUrl = "http://localhost:8080/" + shortCode;
        return new CreateShortUrlResponse(shortCode, shortUrl);
    }
    public Optional<GetShortUrlMetadataResponse> getShortUrlMetadata(String shortCode) {
        Optional<ShortUrl> opt = repository.findByShortCode(shortCode);
        if (opt.isPresent()) {
            ShortUrl url = opt.get();
            return Optional.of(new GetShortUrlMetadataResponse(
                    url.getOriginalUrl(),
                    url.getShortCode(),
                    url.getCreatedAt(),
                    url.getExpiresAt(),
                    url.getClickCount()
            ));
        }
        return Optional.empty();
    }

    public Optional<GetShortUrlAnalyticsResponse> getShortUrlAnalytics(String shortCode) {
        Optional<ShortUrl> opt = repository.findByShortCode(shortCode);
        if (opt.isPresent()) {
            ShortUrl url = opt.get();
            return Optional.of(new GetShortUrlAnalyticsResponse(
                    url.getClickCount(),
                    url.getCreatedAt(),
                    url.getExpiresAt()
            ));
        }
        return Optional.empty();
    }


}
