package us.vicentini.springrestclientexamples.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Card {
    private String type;
    private String number;
    @JsonProperty("expiration_date")
    private ExpirationDate expirationDate;
    private String iban;
    private String swift;
}
