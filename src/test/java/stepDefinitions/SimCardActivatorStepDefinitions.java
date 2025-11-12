package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;


import io.cucumber.java.en.*;
import org.springframework.http.*;
import static org.junit.jupiter.api.Assertions.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;
    private String iccid;
    private String email;

    @Given("a SIM card with ICCID {string} and email {string}")
    public void givenSimCard(String iccid, String email) {
        this.iccid = iccid;
        this.email = email;
    }

    @When("I POST to {string}")
    public void iPostTo(String endpoint) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = String.format("{\"iccid\":\"%s\", \"customerEmail\":\"%s\"}", iccid, email);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        response = restTemplate.postForEntity("http://localhost:8080" + endpoint, entity, String.class);
    }

    @When("I GET with an ID of {int}")
    public void iGet(int id) {
        response = restTemplate.getForEntity("http://localhost:8080/sim/" + id, String.class);
    }


    @Then("the response should contain {string}")
    public void responseShouldContain(String expected) {

        System.out.println("Response body: " + response.getBody());
        assertTrue(response.getBody().contains(expected));
    }

}

