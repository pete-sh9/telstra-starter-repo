package au.com.telstra.simcardactivator;

import au.com.telstra.simcardactivator.repository.SimCardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;


@RestController
public class Controller {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SimCardService service;


    @PostMapping("/activate")
    public ResponseEntity<String> activateSimCard(@RequestBody SimCardRequest request) throws JsonProcessingException {
        String microserviceUrl = "http://localhost:8444/actuate";  // Replace with actual microservice URL

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a minimal payload with only the IccID
        Map<String, String> payload = new HashMap<>();
        payload.put("iccid", request.getIccid());

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(microserviceUrl, entity, String.class);

        SimCardDB simCard = new SimCardDB();
        simCard.setIccid(request.getIccid());
        simCard.setCustomerEmail(request.getCustomerEmail());

        ObjectMapper mapper = new ObjectMapper();
        boolean success = mapper.readTree(response.getBody()).get("success").asBoolean();

        simCard.setActive(success);

        SimCardDB saved = service.save(simCard);
        return ResponseEntity.ok("Saved SIM with ID: " + saved.getId());
    }

    @GetMapping("/sim/{id}")
    public ResponseEntity<SimCardDB> getSimById(@PathVariable Long id) {
        Optional<SimCardDB> simCard = service.findById(id);
        return simCard
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}