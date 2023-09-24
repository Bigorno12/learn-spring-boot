package mu.tutorial.learnspringboot.jsonplaceholder;

import lombok.Builder;

@Builder
public record TodoJsonPlaceHolder(Long userId, Long id, String title, Boolean completed) {
}
