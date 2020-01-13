package us.vicentini.springrestclientexamples;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@Slf4j
public class RestTemplateExamples {

    public static final String API_ROOT = "https://api.predic8.de:443/shop";


    @Test
    public void getCategories() {
        String apiUrl = API_ROOT + "/categories/";

        RestTemplate restTemplate = new RestTemplate();

        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);

        log.info("Response");
        log.info(jsonNode.toString());
    }


    @Test
    public void getCustomers() {
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        JsonNode jsonNode = restTemplate.getForObject(apiUrl, JsonNode.class);

        log.info("Response");
        log.info(jsonNode.toString());
    }


    @Test
    public void createCustomer() {
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Joe");
        postMap.put("lastname", "Buck");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        log.info("Response");
        log.info(jsonNode.toString());
    }


    @Test
    public void updateCustomer() {

        //create customer to update
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Micheal");
        postMap.put("lastname", "Weston");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        log.info("Response");
        log.info(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();

        String id = customerUrl.split("/")[3];

        log.info("Created customer id: " + id);

        postMap.put("firstname", "Micheal 2");
        postMap.put("lastname", "Weston 2");

        restTemplate.put(apiUrl + id, postMap);

        JsonNode updatedNode = restTemplate.getForObject(apiUrl + id, JsonNode.class);

        log.info(updatedNode.toString());

    }


    @Test
    public void updateCustomerUsingPatchSunHttp() {

        //create customer to update
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Sam");
        postMap.put("lastname", "Axe");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        log.info("Response");
        log.info(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();

        String id = customerUrl.split("/")[3];

        log.info("Created customer id: " + id);

        postMap.put("firstname", "Sam 2");
        postMap.put("lastname", "Axe 2");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(postMap, headers);

        //fails due to sun.net.www.protocol.http.HttpURLConnection not supporting patch
        ResourceAccessException ex = assertThrows(ResourceAccessException.class,
                                                  () -> restTemplate
                                                          .patchForObject(apiUrl + id, entity, JsonNode.class));

        assertNotNull(ex);
    }


    @Test
    public void updateCustomerUsingPatch() {

        //create customer to update
        String apiUrl = API_ROOT + "/customers/";

        // Use Apache HTTP client factory
        //see: https://github.com/spring-cloud/spring-cloud-netflix/issues/1777
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Sam");
        postMap.put("lastname", "Axe");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        log.info("Response");
        log.info(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();

        String id = customerUrl.split("/")[3];

        log.info("Created customer id: " + id);

        postMap.put("firstname", "Sam 2");
        postMap.put("lastname", "Axe 2");

        //example of setting headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(postMap, headers);

        JsonNode updatedNode = restTemplate.patchForObject(apiUrl + id, entity, JsonNode.class);

        log.info(updatedNode.toString());
    }


    @Test
    public void deleteCustomer() {

        //create customer to update
        String apiUrl = API_ROOT + "/customers/";

        RestTemplate restTemplate = new RestTemplate();

        //Java object to parse to JSON
        Map<String, Object> postMap = new HashMap<>();
        postMap.put("firstname", "Les");
        postMap.put("lastname", "Claypool");

        JsonNode jsonNode = restTemplate.postForObject(apiUrl, postMap, JsonNode.class);

        log.info("Response");
        log.info(jsonNode.toString());

        String customerUrl = jsonNode.get("customer_url").textValue();

        String id = customerUrl.split("/")[3];

        log.info("Created customer id: " + id);

        restTemplate.delete(apiUrl + id); //expects 200 status

        log.info("Customer deleted");

        //should go boom on 404

        HttpClientErrorException ex = assertThrows(HttpClientErrorException.class,
                                                   () -> restTemplate.getForObject(apiUrl + id, JsonNode.class));

        assertNotNull(ex);
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }


}
