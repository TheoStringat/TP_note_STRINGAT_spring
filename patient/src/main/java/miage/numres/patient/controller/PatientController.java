package miage.numres.patient.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import miage.numres.patient.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    // Cette liste simule une base de données en mémoire
    private List<Patient> patients = new ArrayList<>();
    private Long idCounter = 1L;

    public PatientController() {
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
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patients.stream()
                .filter(p -> p.getId().equals(id))
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
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        Optional<Patient> patient = patients.stream()
                .filter(p -> p.getId().equals(id))
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
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        boolean removed = patients.removeIf(p -> p.getId().equals(id));
        if (removed) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}