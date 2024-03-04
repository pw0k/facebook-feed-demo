package pw.feed.postwriter.service.converter;

import org.springframework.stereotype.Component;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.dto.UserProfileDTO;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserProfileDTOConverter {

    public UserProfileDTO convert(User user) {
        List<String> following = user.getUserFollows().stream()
                .map(follow -> follow.getFollowUser().getUsername())
                .collect(Collectors.toList());
        List<String> followers = user.getFollowerUsers().stream()
                .map(follow -> follow.getUser().getUsername())
                .collect(Collectors.toList());
        return new UserProfileDTO(user.getId(), user.getUsername(), following, followers);
    }
}
