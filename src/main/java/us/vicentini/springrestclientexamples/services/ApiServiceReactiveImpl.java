package us.vicentini.springrestclientexamples.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import us.vicentini.springrestclientexamples.api.domain.User;
import us.vicentini.springrestclientexamples.api.domain.UserData;

@Service
@RequiredArgsConstructor
@ConditionalOnMissingBean(RestTemplate.class)
class ApiServiceReactiveImpl implements ApiService {
    @Value("${api.url}")
    private String apiUrl;


    @Override
    public Flux<User> getUsersReactive(Integer limit) {
        return WebClient.builder().baseUrl(apiUrl).build().get()
                .uri(uriBuilder -> uriBuilder.queryParam("limit", limit).build())
                .retrieve()
                .bodyToFlux(UserData.class)
                .flatMap(userData -> Flux.fromIterable(userData.getData()));
    }
}
