package miage.numres.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/gateway")
public class ApiGatewayController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Récupérer tous les patients via le microservice patient-service
     */
    @GetMapping("/patients")
    @HystrixCommand(fallbackMethod = "getAllPatientsFallback")
    public List<String> getAllPatients() {
        System.out.println("Fetching all patients...");

        List<String> response = restTemplate.exchange(
                "http://patient-service/api/patients",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        ).getBody();

        return response;
    }

    public List<String> getAllPatientsFallback() {
        return Collections.singletonList("Fallback: Patient service unavailable");
    }

    /**
     * Récupérer un patient par son ID via le microservice patient-service
     */
    @GetMapping("/patients/{patientId}")
    @HystrixCommand(fallbackMethod = "getPatientFallback")
    public String getPatientDetails(@PathVariable int patientId) {
        System.out.println("Fetching details for Patient ID: " + patientId);

        String response = restTemplate.exchange(
                "http://patient-service/api/patients/{id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {},
                patientId
        ).getBody();

        return response;
    }

    public String getPatientFallback(@PathVariable int patientId) {
        return "Fallback: Patient service is unavailable for patient ID " + patientId;
    }

    /**
     * Récupérer tous les praticiens via le microservice praticien-service
     */
    @GetMapping("/praticiens")
    @HystrixCommand(fallbackMethod = "getAllPraticiensFallback")
    public List<String> getAllPraticiens() {
        System.out.println("Fetching all praticiens...");

        List<String> response = restTemplate.exchange(
                "http://praticien-service/api/praticiens",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        ).getBody();

        return response;
    }

    public List<String> getAllPraticiensFallback() {
        return Collections.singletonList("Fallback: Praticien service unavailable");
    }

    /**
     * Récupérer un praticien par son ID via le microservice praticien-service
     */
    @GetMapping("/praticiens/{praticienId}")
    @HystrixCommand(fallbackMethod = "getPraticienFallback")
    public String getPraticienDetails(@PathVariable int praticienId) {
        System.out.println("Fetching details for Praticien ID: " + praticienId);

        String response = restTemplate.exchange(
                "http://praticien-service/api/praticiens/{id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {},
                praticienId
        ).getBody();

        return response;
    }

    public String getPraticienFallback(@PathVariable int praticienId) {
        return "Fallback: Praticien service is unavailable for praticien ID " + praticienId;
    }

    /**
     * Bean RestTemplate LoadBalanced pour appeler les services via Eureka + Ribbon.
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}