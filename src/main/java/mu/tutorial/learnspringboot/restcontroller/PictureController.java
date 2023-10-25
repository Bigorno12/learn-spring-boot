package mu.tutorial.learnspringboot.restcontroller;

import mu.tutorial.learnspringboot.picture.Image;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/picture")
public class PictureController {

    @PostMapping("/upload")
    public void testing(@RequestPart(name = "picture") MultipartFile picture, @RequestBody Image image) {
        System.out.println(picture.getContentType() + " " + image);
    }
}
