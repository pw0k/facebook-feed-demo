package pw.feed.postwriter.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pw.feed.postwriter.exception.UserAlreadyExistsException;
import pw.feed.postwriter.model.group.Group;
import pw.feed.postwriter.model.group.GroupRepository;
import pw.feed.postwriter.model.user.User;
import pw.feed.postwriter.model.user.UserRepository;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Transactional
    public boolean followToGroup(Integer userId, Integer groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));

        if (!group.getFollowers().contains(user)) {
            group.addFollower(user);
            groupRepository.save(group);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean unfollowFromGroup(Integer userId, Integer groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));

        if (group.getFollowers().contains(user)) {
            group.removeFollower(user);
            groupRepository.save(group);
            return true;
        }
        return false;
    }

    @Transactional
    public Group save(Group group) {
        if (groupRepository.existsByName(group.getName())) {
            throw new UserAlreadyExistsException("Group with the name " + group.getName() + " already exists.");
        }
        return groupRepository.save(group);
    }

}
