package us.vicentini.springrestclientexamples.services;

import us.vicentini.springrestclientexamples.api.domain.User;

import java.util.List;

public interface ApiService {

    List<User> getUsers(Integer limit);
}
