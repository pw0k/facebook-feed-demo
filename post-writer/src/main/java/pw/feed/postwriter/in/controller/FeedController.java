package pw.feed.postwriter.in.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.feed.postwriter.service.FeedService;
import pw.feed.postwriter.service.dto.PostRecord;

import java.util.List;

@RestController
@RequestMapping("/api/pw/feed")
@RequiredArgsConstructor
//todo rewrite dto
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/{userName}")
    public ResponseEntity<List<PostRecord>> getUserFeed(@PathVariable String userName) {
        return ResponseEntity.ok(feedService.getFeedForUser(userName));
    }
}
