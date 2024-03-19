package pw.feed.postreader.in.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import pw.feed.postreader.config.WebClientConfig;

@WebFluxTest(FeedController.class)
@Import(WebClientConfig.class)
public class FeedControllerTest {

    @Autowired
    private WebTestClient webTestClient;

//    @MockBean
//    private WebClient webClient;

    @Test
    public void testHomeEndpoint() {

        webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[0].title").isEqualTo("title1")
                .jsonPath("$.[0].description").isEqualTo("description1")
                .jsonPath("$.[0].username").isEqualTo("username1")
                .jsonPath("$.[1].title").isEqualTo("title2")
                .jsonPath("$.[4].title").doesNotExist();
    }
}