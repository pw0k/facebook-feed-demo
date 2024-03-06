package pw.feed.postwriter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.feed.postwriter.exception.PostNotFoundException;
import pw.feed.postwriter.model.post.*;
import pw.feed.postwriter.service.converter.PostOutboxConverter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostOutboxRepository postOutboxRepository;
    private final PostOutboxConverter postOutboxConverter;

    @Transactional
    public Post saveWithOutbox(Post post) {
        Post savedPost = postRepository.save(post);

        PostOutbox postOutbox = postOutboxConverter.convert(savedPost, PostOutboxEventType.POST_CREATED);
        postOutboxRepository.save(postOutbox);

        return savedPost;
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

}
