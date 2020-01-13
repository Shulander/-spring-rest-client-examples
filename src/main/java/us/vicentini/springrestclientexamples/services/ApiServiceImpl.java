package us.vicentini.springrestclientexamples.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import us.vicentini.springrestclientexamples.api.domain.User;
import us.vicentini.springrestclientexamples.api.domain.UserData;

import java.net.URI;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
class ApiServiceImpl implements ApiService {

    @Value("${api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;


    @Override
    public List<User> getUsers(Integer limit) {
        if (restTemplate == null) {
            return Collections.emptyList();
        }
        URI uri = UriComponentsBuilder
                .fromHttpUrl(apiUrl)
                .queryParam("limit", limit)
                .build()
                .toUri();
        UserData userData = restTemplate.getForObject(uri, UserData.class);

        return userData.getData();
    }


    @Override
    public Flux<User> getUsersReactive(Integer limit) {
        return WebClient.create(apiUrl)
                .get()
                .uri(uriBuilder -> uriBuilder.queryParam("limit", limit).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(UserData.class)
                .flatMapIterable(UserData::getData);
    }
}
