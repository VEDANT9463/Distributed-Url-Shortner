package com.urlshortner.urlservice.event;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class UrlClickedEvent {

    private String shortCode;
    private String originalUrl;
    private LocalDateTime clickedAt;
    private String userAgent;
    private String ipAddress;
}
