package com.springshorturl.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateShortUrlRequest {
    @NotBlank
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
