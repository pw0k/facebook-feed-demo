package pw.feed.postwriter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserProfileDTO {
    private Integer userId;
    private String username;
    private List<String> followingUsernames;
    private List<String> followerUsernames;

}
