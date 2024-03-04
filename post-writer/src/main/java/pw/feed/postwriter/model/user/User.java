package pw.feed.postwriter.model.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pw.feed.postwriter.model.follow.UserFollow;
import pw.feed.postwriter.model.group.Group;
import pw.feed.postwriter.model.post.Post;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Table(name = "fb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @NotNull
    private String username;

    @Column(nullable = false, unique = true)
    @NotNull
    private String email;

    @ManyToMany(mappedBy = "followers")
    @Builder.Default
    private Set<Group> groups = new HashSet<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private Set<UserFollow> userFollows = new HashSet<>();

    @OneToMany(
            mappedBy = "followUser",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private Set<UserFollow> followerUsers = new HashSet<>();

//    public void addPost(Post post) {
//        posts.add(post);
//        post.setUser(this);
//    }
//
//    public void removePost(Post post) {
//        posts.remove(post);
//        post.setUser(null);
//    }
}
