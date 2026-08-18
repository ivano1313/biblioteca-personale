package it.ivano.biblioteca.controller;

import it.ivano.biblioteca.model.Libro;
import it.ivano.biblioteca.service.LibroService;
import it.ivano.biblioteca.service.OpenLibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/libri")
public class LibroRestController {

    private final LibroService libroService;
    private final OpenLibraryService openLibraryService;

    public LibroRestController(LibroService libroService, OpenLibraryService openLibraryService) {
        this.libroService = libroService;
        this.openLibraryService = openLibraryService;
    }

    // 🔹 GET tutti i libri
    @GetMapping
    public List<Libro> getAllLibri() {
        return libroService.getAllLibri();
    }

    // 🔹 POST aggiungi un libro
    @PostMapping("/add")
    public Libro addLibro(@RequestBody Libro libro) {
        return libroService.addLibro(libro);
    }

    // 🔹 PUT aggiorna un libro
    @PutMapping("/{id}")
    public Libro updateLibro(@PathVariable Integer id, @RequestBody Libro libro) {
        return libroService.updateLibro(id, libro);
    }

    // 🔹 DELETE elimina un libro per ID
    @DeleteMapping("/{id}")
    public String deleteLibroById(@PathVariable Integer id) {
        libroService.deleteLibro(id);
        return "Libro con ID " + id + " eliminato con successo.";
    }

    // 🔹 GET metadati libro da Open Library dato l'ISBN
    @GetMapping("/lookup")
    public ResponseEntity<Map<String, String>> lookupByIsbn(@RequestParam String isbn) {
        Map<String, String> risultato = openLibraryService.cercaPerIsbn(isbn);
        return risultato != null ? ResponseEntity.ok(risultato) : ResponseEntity.notFound().build();
    }

    // 🔹 GET conta per categoria
    @GetMapping("/countByCategoria")
    public long countLibriByCategoria(@RequestParam String categoria) {
        return libroService.getAllLibri().stream()
                .filter(libro -> libro.getCategoria().name().equalsIgnoreCase(categoria))
                .count();
    }

    // 🔹 GET conta per anno
    @GetMapping("/countByAnno")
    public long countLibriByAnno(@RequestParam Integer anno) {
        return libroService.getAllLibri().stream()
                .filter(libro -> libro.getAnnoPubblicazione().getYear() == anno)
                .count();
    }

    // 🔹 GET conta per autore
    @GetMapping("/countByAutore")
    public long countLibriByAutore(@RequestParam String autore) {
        return libroService.getAllLibri().stream()
                .filter(libro -> libro.getAutore().equalsIgnoreCase(autore))
                .count();
    }
}
