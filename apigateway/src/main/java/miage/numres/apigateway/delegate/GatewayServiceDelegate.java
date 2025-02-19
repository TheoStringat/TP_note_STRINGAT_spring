package miage.numres.apigateway.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import java.util.Collections;
import java.util.List;

@Service
public class GatewayServiceDelegate {

    @Autowired
    RestTemplate restTemplate;

    /**
     * Récupérer tous les patients via le microservice patient-service
     */
    @HystrixCommand(fallbackMethod = "getAllPatientsFallback")
    public List<String> getAllPatients() {
        System.out.println("Fetching all patients...");

        return restTemplate.exchange(
                "http://patient-service/api/patients",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        ).getBody();
    }

    public List<String> getAllPatientsFallback() {
        return Collections.singletonList("Fallback: Patient service unavailable");
    }

    /**
     * Récupérer un patient par son ID via le microservice patient-service
     */
    @HystrixCommand(fallbackMethod = "getPatientFallback")
    public String getPatientDetails(int patientId) {
        System.out.println("Fetching details for Patient ID: " + patientId);

        return restTemplate.exchange(
                "http://patient-service/api/patients/{id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {},
                patientId
        ).getBody();
    }

    public String getPatientFallback(int patientId) {
        return "Fallback: Patient service is unavailable for patient ID " + patientId;
    }

    /**
     * Récupérer tous les praticiens via le microservice praticien-service
     */
    @HystrixCommand(fallbackMethod = "getAllPraticiensFallback")
    public List<String> getAllPraticiens() {
        System.out.println("Fetching all praticiens...");

        return restTemplate.exchange(
                "http://praticien-service/api/praticiens",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<String>>() {}
        ).getBody();
    }

    public List<String> getAllPraticiensFallback() {
        return Collections.singletonList("Fallback: Praticien service unavailable");
    }

    /**
     * Récupérer un praticien par son ID via le microservice praticien-service
     */
    @HystrixCommand(fallbackMethod = "getPraticienFallback")
    public String getPraticienDetails(int praticienId) {
        System.out.println("Fetching details for Praticien ID: " + praticienId);

        return restTemplate.exchange(
                "http://praticien-service/api/praticiens/{id}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {},
                praticienId
        ).getBody();
    }

    public String getPraticienFallback(int praticienId) {
        return "Fallback: Praticien service is unavailable for praticien ID " + praticienId;
    }

    @Bean
    @LoadBalanced  // Permet de gérer le load balancing entre les instances de microservices
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
