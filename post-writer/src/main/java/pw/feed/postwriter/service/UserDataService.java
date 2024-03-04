package pw.feed.postwriter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.model.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDataService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getWithFollowsIdsById(Integer userId) {
        return userRepository.findWithFollowsIdsById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
