package com.springshorturl.dto;

import java.time.LocalDateTime;

public class GetShortUrlAnalyticsResponse {
    private Long clickCount;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    // Constructor
    public GetShortUrlAnalyticsResponse(Long clickCount, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.clickCount = clickCount;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    // Getters and setters
    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
