package pw.feed.postwriter.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import pw.feed.postwriter.model.follow.UserFollow;
import pw.feed.postwriter.model.follow.UserFollowId;
import pw.feed.postwriter.model.follow.UserFollowRepository;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.model.user.UserRepository;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final UserRepository userRepository;
    private final UserFollowRepository userFollowRepository;

    /**
     * Establishes a following relationship between two users by creating a new {@link UserFollow} entity.
     * @param userId the ID of the user who wants to follow another user. Must not be null and must refer to an existing user.
     * @param targetUserId the ID of the user to be followed. Must not be null, must refer to an existing user, and must not be the same as {@code userId}.
     * @return {@code true} if the following relationship was successfully created; {@code false} if the relationship already exists.
     * @throws IllegalArgumentException if {@code userId} and {@code targetUserId} are the same, indicating a user attempting to follow themselves,
     *                                  or if either {@code userId} or {@code targetUserId} do not correspond to existing users.
     */
    @Transactional
    public boolean followUser(Integer userId, Integer targetUserId) {
        if (userId.equals(targetUserId)) {
            throw new IllegalArgumentException("Cannot follow oneself");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("Target user not found with id: " + targetUserId));
        UserFollowId userFollowId = new UserFollowId(userId, targetUserId);

        if (!userFollowRepository.existsById(userFollowId)) {
            UserFollow userFollow = UserFollow.builder()
                    .id(userFollowId)
                    .user(user)
                    .followUser(targetUser)
                    .build();
            userFollowRepository.save(userFollow);
            return true;
        }

        return false;
    }

    @Transactional
    public boolean unfollowUser(Integer userId, Integer targetUserId) {
        if (userId.equals(targetUserId)) {
            throw new IllegalArgumentException("Cannot unfollow oneself");
        }

        UserFollowId userFollowId = new UserFollowId(userId, targetUserId);
        if (userFollowRepository.existsById(userFollowId)) {
            userFollowRepository.deleteById(userFollowId);
            return true;
        }
        return false;
    }
}