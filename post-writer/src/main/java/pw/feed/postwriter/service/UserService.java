package pw.feed.postwriter.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pw.feed.postwriter.dto.UserProfileDTO;
import pw.feed.postwriter.exception.UserAlreadyExistsException;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.service.converter.UserProfileDTOConverter;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDataService userDataService;
    private final UserProfileDTOConverter userProfileConverter;

    public UserProfileDTO getUserProfile(Integer userId) {
        return userProfileConverter.convert(userDataService.getWithFollowsIdsById(userId));
    }

    @Transactional
    public User save(User user){
        if (userDataService.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("User with the username " + user.getUsername() + " already exists.");
        }
        return userDataService.save(user);
    }

    public boolean existsByEmail(String email) {
        return userDataService.existsByEmail(email);
    }

}
