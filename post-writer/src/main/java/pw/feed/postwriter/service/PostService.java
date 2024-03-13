package pw.feed.postwriter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.feed.postwriter.exception.PostNotFoundException;
import pw.feed.postwriter.model.post.*;
import pw.feed.postwriter.out.PostOutboxProducer;
import pw.feed.postwriter.service.converter.PostOutboxConverter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostOutboxRepository postOutboxRepository;
    private final PostOutboxConverter postOutboxConverter;
    private final PostOutboxProducer postOutboxProducer;

    @Transactional
    public Post saveWithOutbox(Post post) {
        Post savedPost = postRepository.save(post);

        PostOutbox postOutbox = postOutboxConverter.convert(savedPost, PostOutboxEventType.POST_CREATED);
        postOutboxRepository.save(postOutbox);

        return savedPost;
    }

    @Transactional
    public void saveAllWithOutbox(List<Post> posts) {
        LocalDateTime startInsert = LocalDateTime.now();
        List<Post> savedPosts = postRepository.saveAll(posts);

        List<PostOutbox> postOutboxes = savedPosts.stream()
                .map(savedPost -> postOutboxConverter.convert(savedPost, PostOutboxEventType.POST_CREATED))
                .toList();
        postOutboxRepository.saveAll(postOutboxes);
        LocalDateTime endInsert = LocalDateTime.now();
        log.warn("inserted for {}sec", ChronoUnit.SECONDS.between(startInsert, endInsert));
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("Post not found with id " + id));
    }

    @Transactional
    public Post updateWithOutbox(Long id, Post updatedPost) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
        post.setTitle(updatedPost.getTitle());
        post.setDescription(updatedPost.getDescription());
        Post savedPost = postRepository.save(post);

        PostOutbox postOutbox = postOutboxConverter.convert(savedPost, PostOutboxEventType.POST_UPDATED);
        postOutboxRepository.save(postOutbox);

        return savedPost;
    }

    @Transactional
    public void sendToKafka() {
        List<PostOutbox> postOutboxList = postOutboxRepository.findLimitedPendingPostOutboxesForUpdate(80);
        postOutboxList.forEach(po -> {
            postOutboxProducer.send(po);
            po.setPostOutboxStatus(PostOutboxStatus.PUBLISHED);
            postOutboxRepository.save(po);
        });
    }
}
