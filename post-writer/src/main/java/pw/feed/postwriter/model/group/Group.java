package pw.feed.postwriter.model.group;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pw.feed.postwriter.model.post.Post;
import pw.feed.postwriter.model.user.User;

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
@Table(name = "fb_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private String description;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_group",
            joinColumns = {@JoinColumn(name = "group_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @Builder.Default
    private Set<User> followers = new HashSet<>();

    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<Post> posts= new ArrayList<>();

    public void addFollower(User user) {
        followers.add(user);
        user.getGroups().add(this);
    }


    public void removeFollower(User user) {
        followers.remove(user);
        user.getGroups().remove(this);
    }
}
