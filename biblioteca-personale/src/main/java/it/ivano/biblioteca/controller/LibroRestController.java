package it.ivano.biblioteca.controller;

import it.ivano.biblioteca.model.Libro;
import it.ivano.biblioteca.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libri")
public class LibroRestController {

    private final LibroService libroService;

    public LibroRestController(LibroService libroService) {
        this.libroService = libroService;
    }

    // 🔹 GET tutti i libri
    @GetMapping
    public List<Libro> getAllLibri() {
        return libroService.getAllLibri();
    }

    // 🔹 POST aggiungi un libro
    @PostMapping("/add")
    public Libro addLibro(@Valid @RequestBody Libro libro) {
        return libroService.addLibro(libro);
    }

    // 🔹 PUT aggiorna un libro
    @PutMapping("/{id}")
    public Libro updateLibro(@PathVariable Integer id, @Valid @RequestBody Libro libro) {
        return libroService.updateLibro(id, libro);
    }

    // 🔹 DELETE elimina un libro per ID
    @DeleteMapping("/{id}")
    public String deleteLibroById(@PathVariable Integer id) {
        libroService.deleteLibro(id);
        return "Libro con ID " + id + " eliminato con successo.";
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
