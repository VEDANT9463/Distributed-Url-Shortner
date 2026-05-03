package com.urlshortner.urlanalytics.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlClickedEvent {
    private String shortCode;
    private String originalUrl;
    private LocalDateTime clickedAt;
    private String ipAddress;
    private String userAgent;
}
