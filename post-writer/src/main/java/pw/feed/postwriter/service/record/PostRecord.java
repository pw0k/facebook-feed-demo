package pw.feed.postwriter.service.record;

import java.time.Instant;

public record PostRecord(String title, String description, Instant createdAt) {}
