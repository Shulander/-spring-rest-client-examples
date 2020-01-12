package us.vicentini.springrestclientexamples.api.domain;

import lombok.Data;

import java.util.List;

@Data
public class UserData {
    private List<User> data;
}
