package com.urlshortner.urlservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUrlResponse {
    private String originalUrl;

    private String shortCode;

    private String shortUrl;
}
