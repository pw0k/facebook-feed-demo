package pw.feed.postreader.in.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;


@Slf4j
@Component
public class PostOutboxConsumer {

    private final ReceiverOptions<String, String> receiverOptions;

    public PostOutboxConsumer(ReceiverOptions<String, String> receiverOptions) {
        this.receiverOptions = receiverOptions;
        consumeMessages().subscribe();
    }

    private Flux<String> consumeMessages() {

        KafkaReceiver<String, String> receiver = KafkaReceiver.create(receiverOptions);

        return receiver.receive()
                .map(ReceiverRecord::value)
                .doOnNext(message -> log.info("Received: " + message));
    }

}