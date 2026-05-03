package com.urlshortner.urlservice.producer;



//@Service
//@RequiredArgsConstructor
//public class UrlClickProducer {
//
//    private final KafkaTemplate<String, String> kafkaTemplate;
//    private final ObjectMapper objectMapper;
//
//    public void sendClickEvent(UrlClickedEvent event) {
//        try {
//            String payload = objectMapper.writeValueAsString(event);
//            kafkaTemplate.send("url-clicked-topic", event.getShortCode(), payload);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Failed to serialize UrlClickedEvent", e);
//        }
//    }
//}

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.urlshortner.urlservice.event.UrlClickedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlClickProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendClickEvent(UrlClickedEvent event) {

        log.info("Preparing to send Kafka event for shortCode: {}", event.getShortCode());

        try {
            String payload = objectMapper.writeValueAsString(event);

            kafkaTemplate.send("url-clicked-topic", event.getShortCode(), payload)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            log.error("Failed to send Kafka event", ex);
                        } else {
                            log.info("Kafka event sent successfully. Topic: {}, Offset: {}",
                                    result.getRecordMetadata().topic(),
                                    result.getRecordMetadata().offset());
                        }
                    });

        } catch (JsonProcessingException e) {
            log.error("Serialization failed for event: {}", event, e);
            throw new RuntimeException("Failed to serialize UrlClickedEvent", e);
        }
    }
}
