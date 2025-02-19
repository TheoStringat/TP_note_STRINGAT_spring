package miage.numres.apigateway.controller;

import miage.numres.apigateway.delegate.GatewayServiceDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gateway")
public class ApiGatewayController {

    @Autowired
    private GatewayServiceDelegate apiGatewayService;

    /**
     * Récupérer tous les patients
     */
    @GetMapping("/patients")
    public List<String> getAllPatients() {
        return apiGatewayService.getAllPatients();
    }

    /**
     * Récupérer un patient par ID
     */
    @GetMapping("/patients/{patientId}")
    public String getPatientDetails(@PathVariable int patientId) {
        return apiGatewayService.getPatientDetails(patientId);
    }

    /**
     * Récupérer tous les praticiens
     */
    @GetMapping("/praticiens")
    public List<String> getAllPraticiens() {
        return apiGatewayService.getAllPraticiens();
    }

    /**
     * Récupérer un praticien par ID
     */
    @GetMapping("/praticiens/{praticienId}")
    public String getPraticienDetails(@PathVariable int praticienId) {
        return apiGatewayService.getPraticienDetails(praticienId);
    }
}