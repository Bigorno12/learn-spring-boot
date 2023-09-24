package mu.tutorial.learnspringboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

    private final RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public RestTemplateConfiguration(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder
                .rootUri("https://jsonplaceholder.typicode.com/")
                .build();
    }
}
