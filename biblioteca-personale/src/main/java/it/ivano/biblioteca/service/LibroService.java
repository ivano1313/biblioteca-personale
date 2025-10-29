package it.ivano.biblioteca.service;

import it.ivano.biblioteca.model.Libro;
import it.ivano.biblioteca.repository.LibroRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LibroService {

    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    /**
     * Recupera tutti i libri dalla biblioteca
     */
    public List<Libro> getAllLibri() {
        log.debug("Recupero di tutti i libri");
        return libroRepository.findAll();
    }

    /**
     * Recupera un libro per ID
     * @throws EntityNotFoundException se il libro non esiste
     */
    public Libro getLibroById(Integer id) {
        log.debug("Ricerca libro con ID: {}", id);
        return libroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Libro non trovato con ID: " + id));
    }

    /**
     * Aggiunge un nuovo libro alla biblioteca
     */
    public Libro addLibro(Libro libro) {
        log.info("Aggiunta nuovo libro: {}", libro.getTitolo());
        return libroRepository.save(libro);
    }

    /**
     * Aggiorna un libro esistente
     * @throws EntityNotFoundException se il libro non esiste
     */
    public Libro updateLibro(Integer id, Libro libro) {
        log.info("Aggiornamento libro con ID: {}", id);

        if (!libroRepository.existsById(id)) {
            throw new EntityNotFoundException("Impossibile aggiornare. Libro non trovato con ID: " + id);
        }

        libro.setId(id);
        return libroRepository.save(libro);
    }

    /**
     * Elimina un libro per ID
     * @throws EntityNotFoundException se il libro non esiste
     */
    public void deleteLibro(Integer id) {
        log.info("Eliminazione libro con ID: {}", id);

        if (!libroRepository.existsById(id)) {
            throw new EntityNotFoundException("Impossibile eliminare. Libro non trovato con ID: " + id);
        }

        libroRepository.deleteById(id);
    }
}
