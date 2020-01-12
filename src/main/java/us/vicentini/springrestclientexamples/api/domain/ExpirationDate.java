package us.vicentini.springrestclientexamples.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExpirationDate {
    private String date;
    @JsonProperty("timezone_type")
    private Integer timezoneType;
    private String timezone;
}
