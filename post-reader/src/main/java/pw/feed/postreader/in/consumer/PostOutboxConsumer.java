package pw.feed.postreader.in.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import pw.feed.postreader.model.PostOutbox;
import reactor.core.publisher.Flux;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostOutboxConsumer {

    private final ReactiveKafkaConsumerTemplate<String, PostOutbox> reactiveKafkaConsumerTemplate;

    @EventListener(ApplicationStartedEvent.class)
    public Flux<PostOutbox> startKafkaConsumer() {
        log.info("consumer started");
        return reactiveKafkaConsumerTemplate
                .receiveAutoAck()
                // .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
                .doOnNext(consumerRecord -> log.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
                )
                .map(ConsumerRecord::value)
                .doOnNext(employee -> log.info("successfully consumed {}={}", PostOutbox.class.getSimpleName(), employee))
                .doOnError(throwable -> log.error("something bad happened while consuming : {}", throwable.getMessage()));
    }

}

//private final ReceiverOptions<String, String> receiverOptions;
//    private final ObjectMapper objectMapper;
//
//    public PostOutboxConsumer(ReceiverOptions<String, String> receiverOptions, ObjectMapper objectMapper) {
//        this.receiverOptions = receiverOptions;
//        this.objectMapper = objectMapper;
//        consumeMessages().subscribe();
//    }
//
//    private Flux<PostOutbox> consumeMessages() {
//        return KafkaReceiver.create(receiverOptions)
//                .receive()
//                .map(record -> {
//                    try {
//                        return objectMapper.readValue(record.value(), PostOutbox.class);
//                    } catch (Exception e) {
//                        throw new RuntimeException("Failed to deserialize PostOutbox message", e);
//                    }
//                })
//                .doOnNext(postOutbox -> log.info("Received: " + postOutbox));
//    }