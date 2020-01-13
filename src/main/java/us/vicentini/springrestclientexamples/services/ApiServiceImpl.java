package us.vicentini.springrestclientexamples.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import us.vicentini.springrestclientexamples.api.domain.User;
import us.vicentini.springrestclientexamples.api.domain.UserData;

import java.net.URI;
import java.util.List;


@Service
@RequiredArgsConstructor
@ConditionalOnBean(RestTemplate.class)
class ApiServiceImpl implements ApiService {

    @Value("${api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;


    @Override
    public List<User> getUsers(Integer limit) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(apiUrl)
                .queryParam("limit", limit)
                .build()
                .toUri();
        UserData userData = restTemplate.getForObject(uri, UserData.class);

        return userData.getData();
    }
}
