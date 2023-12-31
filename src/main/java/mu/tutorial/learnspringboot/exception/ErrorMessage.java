package mu.tutorial.learnspringboot.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorMessage(int statusCode, LocalDateTime timestamp, String message, String description) { }
