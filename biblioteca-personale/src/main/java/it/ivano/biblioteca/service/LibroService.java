package it.ivano.biblioteca.service;

import it.ivano.biblioteca.model.Libro;
import it.ivano.biblioteca.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    // Aggiungiamo i metodi CRUD per gestire i libri
    public List<Libro> getAllLibri() {
        return libroRepository.findAll();
    }
    public Libro getLibroById(Integer id) {
        return libroRepository.findById(id).orElse(null);
    }
    public Libro addLibro(Libro libro) {
        return libroRepository.save(libro);
    }
    public Libro updateLibro(Integer id, Libro libro) {
        if (libroRepository.existsById(id)) {
            libro.setId(id);
            return libroRepository.save(libro);
        }
        return null;
    }
    public void deleteLibro(Integer id) {
        libroRepository.deleteById(id);
    }

}
