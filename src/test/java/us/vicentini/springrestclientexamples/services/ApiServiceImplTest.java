package us.vicentini.springrestclientexamples.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import us.vicentini.springrestclientexamples.api.domain.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApiServiceImplTest {
    @Autowired
    ApiService apiService;


    @Test
    void testGetUsers() {
        List<User> users = apiService.getUsers(1);
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }
}
