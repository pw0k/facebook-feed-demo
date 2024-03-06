package pw.feed.postwriter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pw.feed.postwriter.dto.UserProfileDTO;
import pw.feed.postwriter.exception.UserAlreadyExistsException;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.model.user.UserRepository;
import pw.feed.postwriter.service.converter.UserProfileDTOConverter;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileDTOConverter userProfileConverter;

    @Transactional(readOnly = true)
    public UserProfileDTO getUserProfile(Integer userId) {
        User user =  userRepository.findWithFollowsIdsById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        return userProfileConverter.convert(user);
    }

    @Transactional
    public User save(User user){
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("User with the username " + user.getUsername() + " already exists.");
        }
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
