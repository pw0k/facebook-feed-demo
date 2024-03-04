package pw.feed.postreader.service.dto;

import java.time.Instant;

public record PostRecord(String title, String description, Instant createdAt) {}
