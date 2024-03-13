package pw.feed.postwriter.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pw.feed.postwriter.model.post.Post;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.model.user.UserRepository;
import pw.feed.postwriter.service.PostService;

import java.time.LocalTime;
import java.util.List;
import java.util.SplittableRandom;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostGeneratorScheduler {

    private final PostService postService;
    private final UserRepository userRepository;

    @Scheduled(initialDelay = 10000, fixedDelay = 7000)
    public void sendPendingPostOutboxMessages() {
        postService.saveAllWithOutbox(generatePosts());
    }


    private List<Post> generatePosts() {
        User user = userRepository.findByUsername("мeeseeks")
                .orElseThrow(() -> new RuntimeException("cant find Meeseeks!"));

        SplittableRandom splittableRandom = new SplittableRandom();
        int randomNumber = 50 + splittableRandom.nextInt(50);
        log.warn("Start generating {} Post: ", randomNumber);

        return IntStream.range(0, randomNumber)
                .mapToObj(i -> createPost(user))
                .toList();
    }

    private Post createPost(User user) {
        return Post.builder()
                .title(" I'm Mr. Meeseeks " + LocalTime.now().toNanoOfDay() / 1000000 )
                .description("Look at me!")
                .user(user)
                .build();
    }
}
