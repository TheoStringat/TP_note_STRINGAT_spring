package miage.numres.praticien.controller;

import miage.numres.praticien.model.Praticien;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/praticiens")
public class PraticienController {

    // Cette liste simule une base de données en mémoire
    private List<Praticien> praticiens = new ArrayList<>();
    private Long idCounter = 1L;

    // Initialisation de la liste avec 4 praticiens
    public PraticienController() {
        praticiens.add(new Praticien(idCounter++, "Dr. House"));
        praticiens.add(new Praticien(idCounter++, "Dr. Watson"));
        praticiens.add(new Praticien(idCounter++, "Dr. Brown"));
        praticiens.add(new Praticien(idCounter++, "Dr. Jane"));
    }

    @GetMapping
    public List<Praticien> getAllPraticiens() {
        return praticiens;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Praticien> getPraticienById(@PathVariable Long id) {
        Optional<Praticien> praticien = praticiens.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        return praticien.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Praticien> createPraticien(@RequestBody Praticien praticien) {
        praticien.setId(idCounter++);
        praticiens.add(praticien);
        return ResponseEntity.ok(praticien);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Praticien> updatePraticien(@PathVariable Long id, @RequestBody Praticien praticienDetails) {
        Optional<Praticien> praticien = praticiens.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (praticien.isPresent()) {
            Praticien updatedPraticien = praticien.get();
            updatedPraticien.setNom(praticienDetails.getNom());
            // Mettez à jour d'autres champs selon votre modèle Praticien si nécessaire
            return ResponseEntity.ok(updatedPraticien);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePraticien(@PathVariable Long id) {
        boolean removed = praticiens.removeIf(p -> p.getId().equals(id));
        if (removed) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
