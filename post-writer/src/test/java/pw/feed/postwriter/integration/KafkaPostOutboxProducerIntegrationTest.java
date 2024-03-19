package pw.feed.postwriter.integration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pw.feed.postwriter.config.KafkaTestConfig;
import pw.feed.postwriter.model.post.PostOutbox;
import pw.feed.postwriter.out.PostOutboxProducer;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pw.feed.postwriter.util.Faker.createPostOutbox;

@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = KafkaTestConfig.class)
public class KafkaPostOutboxProducerIntegrationTest {
    @Container
    static final KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.6.0")
    );
    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Autowired
    private PostOutboxProducer postOutboxProducer;
    @Autowired
    private ConsumerFactory<String, PostOutbox> consumerFactory;
    @Value("${topic.post-outbox.name}")
    private String topicName;

    @Test
    public void it_should_send_post_outbox() {
        // Given
        PostOutbox postOutbox = createPostOutbox("titleTitle");
        Consumer<String, PostOutbox> consumer = consumerFactory.createConsumer();
        consumer.subscribe(Collections.singletonList(topicName));

        // When
        postOutboxProducer.send(postOutbox);
        ConsumerRecord<String, PostOutbox> received = KafkaTestUtils.getSingleRecord(consumer, topicName);

        // Then
        assertEquals(postOutbox.getTitle(), received.value().getTitle());

        consumer.close();
    }
}
