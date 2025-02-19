package miage.numres.apigateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ApiGatewayController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Récupère les détails d'un patient en appelant le microservice "patient-service".
     */
    @RequestMapping(value = "/patientDetails/{patientId}", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "getPatientFallback")
    public String getPatientDetails(@PathVariable Long patientId) {
        System.out.println("Getting Patient details for ID: " + patientId);

        // Appel REST vers le microservice "patient-service" : "/api/patients/{id}"
        String response = restTemplate.exchange(
                "http://patient-service/api/patients/{id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {},
                patientId
        ).getBody();

        System.out.println("Patient-service response: " + response);

        return "Patient ID: " + patientId + " [Patient Details: " + response + "]";
    }

    /**
     * Fallback method si l'appel à patient-service échoue ou prend trop de temps.
     */
    public String getPatientFallback(@PathVariable Long patientId) {
        return "Fallback response: Patient service is unavailable for patient ID " + patientId;
    }

    /**
     * Récupère les détails d'un praticien en appelant le microservice "praticien-service".
     */
    @RequestMapping(value = "/praticienDetails/{praticienId}", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "getPraticienFallback")
    public String getPraticienDetails(@PathVariable Long praticienId) {
        System.out.println("Getting Praticien details for ID: " + praticienId);

        // Appel REST vers le microservice "praticien-service" : "/api/praticiens/{id}"
        String response = restTemplate.exchange(
                "http://praticien-service/api/praticiens/{id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {},
                praticienId
        ).getBody();

        System.out.println("Praticien-service response: " + response);

        return "Praticien ID: " + praticienId + " [Praticien Details: " + response + "]";
    }

    /**
     * Fallback method si l'appel à praticien-service échoue ou prend trop de temps.
     */
    public String getPraticienFallback(@PathVariable Long praticienId) {
        return "Fallback response: Praticien service is unavailable for praticien ID " + praticienId;
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
