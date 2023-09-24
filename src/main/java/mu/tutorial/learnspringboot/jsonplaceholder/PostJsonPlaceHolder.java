package mu.tutorial.learnspringboot.jsonplaceholder;

import lombok.Builder;

@Builder
public record PostJsonPlaceHolder(Long userId, Long id, String title, String body) {
}
