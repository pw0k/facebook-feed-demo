package pw.feed.postreader.in.controller;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostDao {
    String title;
    String description;
    String username;
    LocalDateTime createdAt;
}
