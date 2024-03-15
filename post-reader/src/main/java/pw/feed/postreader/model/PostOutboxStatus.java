package pw.feed.postreader.model;

public enum PostOutboxStatus {
    PENDING, // Initial state, waiting to be processed.
    PUBLISHED, // Successfully published to the message broker or event bus.
    FAILED // Indicates a failure in processing or publishing the event.
}