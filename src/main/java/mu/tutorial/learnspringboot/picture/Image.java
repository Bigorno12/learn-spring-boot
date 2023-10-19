package mu.tutorial.learnspringboot.picture;

import lombok.Builder;
import org.springframework.core.io.Resource;

import java.time.LocalDate;
import java.util.UUID;

@Builder
public record Image(UUID shopId, UUID imageId, Resource resource, LocalDate createdOn) {
}
