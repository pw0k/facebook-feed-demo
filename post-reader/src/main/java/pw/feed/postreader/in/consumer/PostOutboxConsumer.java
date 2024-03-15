package pw.feed.postreader.in.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pw.feed.postreader.model.PostOutbox;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;

@Slf4j
@Component
public class PostOutboxConsumer {

    private final ReceiverOptions<String, String> receiverOptions;
    private final ObjectMapper objectMapper;

    public PostOutboxConsumer(ReceiverOptions<String, String> receiverOptions, ObjectMapper objectMapper) {
        this.receiverOptions = receiverOptions;
        this.objectMapper = objectMapper;
        consumeMessages().subscribe();
    }

    private Flux<PostOutbox> consumeMessages() {
        return KafkaReceiver.create(receiverOptions)
                .receive()
                .map(record -> {
                    try {
                        return objectMapper.readValue(record.value(), PostOutbox.class);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to deserialize PostOutbox message", e);
                    }
                })
                .doOnNext(postOutbox -> log.info("Received: " + postOutbox));
    }

}