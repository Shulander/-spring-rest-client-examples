package us.vicentini.springrestclientexamples.services;

import reactor.core.publisher.Flux;
import us.vicentini.springrestclientexamples.api.domain.User;

import java.util.Collections;
import java.util.List;

public interface ApiService {

    default List<User> getUsers(Integer limit) {
        return Collections.emptyList();
    }

    default Flux<User> getUsersReactive(Integer limit) {
        return Flux.empty();
    }
}
