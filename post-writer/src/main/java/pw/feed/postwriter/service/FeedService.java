package pw.feed.postwriter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.feed.postwriter.model.group.Group;
import pw.feed.postwriter.model.post.Post;
import pw.feed.postwriter.model.post.PostRepository;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.model.user.UserRepository;
import pw.feed.postwriter.service.dto.PostRecord;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<PostRecord> getFeedForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("User not found : " + userName));

        List<Integer> userFollowsIds = user.getUserFollows().stream()
                .map(userFollow -> userFollow.getFollowUser().getId())
                .collect(Collectors.toList());

        List<Integer> userGroupIds = user.getGroups().stream()
                .map(Group::getId)
                .collect(Collectors.toList());

        return postRepository.findPostsByFollowedUsersOrGroups(userFollowsIds, userGroupIds).stream()
                .map(this::convertToPostRecord)
                .sorted(Comparator.comparing(PostRecord::createdAt).reversed())
                .toList();
    }

    private PostRecord convertToPostRecord(Post post) {
        return new PostRecord(post.getTitle(), post.getDescription(), post.getCreatedAt());
    }
}
