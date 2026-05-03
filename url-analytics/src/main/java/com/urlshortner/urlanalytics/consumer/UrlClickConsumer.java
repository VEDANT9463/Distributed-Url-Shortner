package com.urlshortner.urlanalytics.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.urlshortner.urlanalytics.entity.UrlAnalytics;
import com.urlshortner.urlanalytics.event.UrlClickedEvent;
import com.urlshortner.urlanalytics.repository.UrlAnalyticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class UrlClickConsumer {
//    private final UrlAnalyticsRepository urlAnalyticsRepository;
//
//    @KafkaListener(topics = "url-clicked-topic",groupId = "analytics-service")
//    public void consume(UrlClickedEvent event){
//        UrlAnalytics analytics = UrlAnalytics.builder()
//                .shortCode(event.getShortCode())
//                .originalUrl(event.getOriginalUrl())
//                .clickedAt(event.getClickedAt())
//                .ipAddress(event.getIpAddress())
//                .userAgent(event.getUserAgent())
//                .build();
//
//        urlAnalyticsRepository.save(analytics);
//
//        log.info("Saved analytics to DB for shortCode: {}", event.getShortCode());
//    }
//}

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlClickConsumer {

    private final ObjectMapper objectMapper;
    private final UrlAnalyticsRepository analyticsRepository;

    @KafkaListener(topics = "url-clicked-topic", groupId = "analytics-service")
    public void consume(String payload) throws JsonProcessingException {

        log.info("Received raw Kafka payload: {}", payload);

        UrlClickedEvent event = objectMapper.readValue(payload, UrlClickedEvent.class);

        UrlAnalytics analytics = UrlAnalytics.builder()
                .shortCode(event.getShortCode())
                .originalUrl(event.getOriginalUrl())
                .clickedAt(event.getClickedAt())
                .ipAddress(event.getIpAddress())
                .userAgent(event.getUserAgent())
                .build();

        analyticsRepository.save(analytics);

        log.info("Saved analytics to DB for shortCode: {}", event.getShortCode());
    }
}
