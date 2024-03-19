package pw.feed.postreader.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

//    private final WebClient webClient;
//    @GetMapping("/{userName}")
//    public Flux<PostRecord> getUserFeed(@PathVariable String userName) {
//        return webClient.get()
//                .uri("/api/pw/feed/{userName}", userName)
//                .retrieve()
//                .bodyToFlux(PostRecord.class); // Assuming Post class is defined and matches the structure of the data
//    }

//    @Autowired
//    private WebTestClient webTestClient;
//
//    @MockBean
//    private WebClient webClient;
//
//    @MockBean
//    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
//
//    @MockBean
//    private WebClient.RequestHeadersSpec requestHeadersSpec;
//
//    @MockBean
//    private WebClient.ResponseSpec responseSpec;
//
//    @Test
//    public void getUserFeed_ShouldReturnFeed_WhenCalledWithUserName() {
//        // Mock the WebClient behavior
//        PostRecord expectedPost = new PostRecord("title", "description", Instant.now());
//        Flux<PostRecord> postRecordFlux = Flux.just(expectedPost);
//
//        when(webClient.get()).thenReturn(requestHeadersUriSpec);
//        when(requestHeadersUriSpec.uri(anyString(), anyString())).thenReturn(requestHeadersSpec);
//        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
//        when(responseSpec.bodyToFlux(PostRecord.class)).thenReturn(postRecordFlux);
//
//        // Call the endpoint and verify the response
//        webTestClient.get().uri("/api/feed/userName")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$[0].title").isEqualTo(expectedPost.title())
//                .jsonPath("$[0].description").isEqualTo(expectedPost.description())
//                .jsonPath("$[0].username").isEqualTo(expectedPost.username());
//    }

}
