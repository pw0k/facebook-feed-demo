package pw.feed.postreader.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pw.feed.postreader.config.KafkaTestConfig;
import pw.feed.postreader.in.consumer.PostOutboxConsumer;
import pw.feed.postreader.model.PostOutbox;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = KafkaTestConfig.class)
public class KafkaPostOutboxConsumerIntegrationTest {

    @Container
    static final KafkaContainer kafkaContainer = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.6.0"));

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Autowired
    private ReactiveKafkaProducerTemplate<String, PostOutbox> reactiveKafkaProducerTemplate;
    @Autowired
    private PostOutboxConsumer postOutboxConsumer;
    @Value("${topic.post-outbox.name}")
    private String topicName;

//    @Test
//    public void it_should_consume_post_outbox_messages() {
//        // Given
//        PostOutbox testMessage = createPostOutbox("titleTitle");
//        Flux<PostOutbox> consumedFlux = postOutboxConsumer.startKafkaConsumer().share();
//        consumedFlux.subscribe();
//
//        // When
//        reactiveKafkaProducerTemplate.send(topicName, testMessage)
//                .doOnSuccess(senderResult -> System.out.println("Message sent to topic: " + topicName))
//                .block(); // Ensure message is sent before proceeding
//
//        // Then
//        StepVerifier.create(consumedFlux)
//                .expectSubscription()
//                .thenRequest(1) // Request 1 item to be processed
//                .expectNextMatches(postOutbox -> postOutbox.equals(testMessage))
//                .thenCancel() // Cancel after receiving the expected item
//                .verify(Duration.ofSeconds(10)); // Set a reasonable timeout
//    }

    // When
//        Mono<Void> sendMono = reactiveKafkaProducerTemplate.send(topicName, testMessage)
//                .doOnSuccess(senderResult -> System.out.println("Message sent to topic: " + topicName))
//                .then();
}