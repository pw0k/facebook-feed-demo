package pw.feed.postwriter.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.feed.postwriter.model.post.Post;
import pw.feed.postwriter.service.FeedService;
import pw.feed.postwriter.service.record.PostRecord;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
//todo rewrite dto
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<PostRecord>> getUserFeed(@PathVariable String username) {
        return ResponseEntity.ok(feedService.getFeedForUser(username));
    }
}
