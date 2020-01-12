package us.vicentini.springrestclientexamples.api.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Card {
    private String type;
    private String number;
    private LocalDate expirationDate;
    private String iban;
    private String swift;
}
