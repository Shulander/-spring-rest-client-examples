package us.vicentini.springrestclientexamples.services;

import lombok.RequiredArgsConstructor;
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

    private static final String EXTERNAL_SERVICE =
            "http://private-anon-1eb7500bda-apifaketory.apiary-mock.com/api/user?limit=";


    @Override
    public Flux<User> getUsersReactive(Integer limit) {

        String url = EXTERNAL_SERVICE + limit;

        return WebClient.builder().build().get()
                .uri(url)
                .retrieve()
                .bodyToFlux(UserData.class)
                .flatMap(userData -> Flux.fromIterable(userData.getData()));
    }
}
