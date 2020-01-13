package us.vicentini.springrestclientexamples.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import us.vicentini.springrestclientexamples.api.domain.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApiServiceTest {

    @Autowired
    ApiService apiService;

    @Autowired(required = false)
    RestTemplate restTemplate;


    @Test
    void testGetUsers() {
        List<User> users = getUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }


    private List<User> getUsers() {
        if (restTemplate == null) {
            return apiService.getUsersReactive(1).collectList().block();
        } else {
            return apiService.getUsers(1);
        }
    }

}
