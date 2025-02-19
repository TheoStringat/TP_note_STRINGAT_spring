package miage.numres.patient.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import miage.numres.patient.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientServiceController {

    // Cette liste simule une base de données en mémoire
    private List<Patient> patients = new ArrayList<>();
    private int idCounter = 0;

    public PatientServiceController() {
        // Initialisation de la liste avec 4 patients
        patients.add(new Patient(idCounter++, "John Doe"));
        patients.add(new Patient(idCounter++, "Jane Smith"));
        patients.add(new Patient(idCounter++, "Bob Johnson"));
        patients.add(new Patient(idCounter++, "Alice Brown"));
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patients;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable int id) {
        Optional<Patient> patient = patients.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
        return patient.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        patient.setId(idCounter++);
        patients.add(patient);
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable int id, @RequestBody Patient patientDetails) {
        Optional<Patient> patient = patients.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if (patient.isPresent()) {
            Patient updatedPatient = patient.get();
            updatedPatient.setName(patientDetails.getName());
            // Mettez à jour d'autres champs selon votre modèle Patient
            return ResponseEntity.ok(updatedPatient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable int id) {
        boolean removed = patients.removeIf(p -> p.getId() == id);
        if (removed) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}