package mu.tutorial.learnspringboot.picture;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
public class Picture {
    public static void main(String[] args) {
        String image = "https://images.unsplash.com/photo-1695582341851-8cc6eba24e1a?auto=format&fit=crop&q=80&w=2070&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
        Image.ImageBuilder imageBuilder = Image.builder();
        try {
            URL url = new URL(image);
            try (InputStream inputStream = url.openStream()) {
                byte[] imageData = inputStream.readAllBytes();
                ByteArrayResource byteArrayResource = new ByteArrayResource(imageData);
                imageBuilder
                        .shopId(UUID.randomUUID())
                        .imageId(UUID.randomUUID())
                        .createdOn(LocalDate.now())
                        .resource(byteArrayResource);

            } catch (IOException e) {
                log.error("Error opening stream: {}", image);
            }
        } catch (MalformedURLException e) {
            log.error("Malformed URL: {}", image);
        }

        Image build = imageBuilder.build();

        try {
            byte[] contentAsByteArray = build.resource().getContentAsByteArray();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(contentAsByteArray);
            String format= "png";
            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

            if (bufferedImage != null) {
                File file = new File("image." + format);
                ImageIO.write(bufferedImage, format, file);
                log.info("Image saved to: {}", file.getAbsolutePath());
            } else {
                log.error("BufferedImage is null");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
