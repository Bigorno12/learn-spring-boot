package mu.tutorial.learnspringboot.jsonplaceholder;

import lombok.Builder;

@Builder
public record CommentJsonPlaceHolder(Long postId, Long id, String name, String email, String body) {
}
