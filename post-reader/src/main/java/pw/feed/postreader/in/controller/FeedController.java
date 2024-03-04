package pw.feed.postreader.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import pw.feed.postreader.service.dto.PostRecord;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedController {

    private final WebClient webClient;
//    private final static String YML = "YML";

//    @GetMapping("/")
//    public Flux<PostDao> home() {
//        List<PostDao> posts = List.of(createPost(1), createPost(2), createPost(3), createPost(4));
//        return Flux.fromIterable(posts);
//    }

//    @GetMapping("/{userId}")
//    public Flux<PostRecord> fetchUserFeed(@PathVariable Integer userId) {
//        return getUserFeed(userId);
//    }

//    private PostDao createPost(Integer num){
//        return PostDao.builder()
//                .username("username" + num)
//                .title("title" + num)
//                .description("description" + num)
//                .createdAt(LocalDateTime.of(2024,02,01,14,00,00))
//                .build();
//    }
    @GetMapping("/{userName}")
    public Flux<PostRecord> getUserFeed(@PathVariable String userName) {
        return webClient.get()
                .uri("/api/pw/feed/{userName}", userName)
                .retrieve()
                .bodyToFlux(PostRecord.class); // Assuming Post class is defined and matches the structure of the data
    }

}
