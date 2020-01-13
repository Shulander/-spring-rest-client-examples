package us.vicentini.springrestclientexamples.services;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import us.vicentini.springrestclientexamples.api.domain.User;
import us.vicentini.springrestclientexamples.api.domain.UserData;

import java.util.List;


@Service
@RequiredArgsConstructor
@ConditionalOnBean(RestTemplate.class)
class ApiServiceImpl implements ApiService {

    private final RestTemplate restTemplate;


    @Override
    public List<User> getUsers(Integer limit) {
        String url = "http://private-anon-1eb7500bda-apifaketory.apiary-mock.com/api/user?limit=" + limit;
        UserData userData = restTemplate.getForObject(url, UserData.class);

        return userData.getData();
    }
}
