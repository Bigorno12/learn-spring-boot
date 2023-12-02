package mu.tutorial.learnspringboot.config;

import mu.tutorial.learnspringboot.service.JsonPlaceHolderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

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
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Bean("restClient")
    public RestClient restClient(RestClient.Builder restClient) {
        return restClient.baseUrl("https://jsonplaceholder.typicode.com/")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @Bean("jsonPlaceHolderClient")
    public JsonPlaceHolderClient jsonPlaceHolderClient(RestClient restClient) {
        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(JsonPlaceHolderClient.class);
    }
}
