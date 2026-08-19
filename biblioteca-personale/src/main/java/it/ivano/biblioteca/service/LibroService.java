package it.ivano.biblioteca.service;

import it.ivano.biblioteca.model.Categoria;
import it.ivano.biblioteca.model.Libro;
import it.ivano.biblioteca.model.StatoLettura;
import it.ivano.biblioteca.repository.LibroRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Libro> getLibriByTitolo(String titolo) {
        return libroRepository.findByTitoloContainingIgnoreCase(titolo);
    }

    // Ricerca con filtri cumulabili (titolo, stato, categoria, valutazione) e ordinamento
    public List<Libro> cercaLibri(String titolo, StatoLettura stato, Categoria categoria, Integer valutazione, String sort) {
        Specification<Libro> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (titolo != null && !titolo.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("titolo")), "%" + titolo.toLowerCase() + "%"));
            }
            if (stato != null) {
                predicates.add(cb.equal(root.get("statoLettura"), stato));
            }
            if (categoria != null) {
                predicates.add(cb.equal(root.get("categoria"), categoria));
            }
            if (valutazione != null) {
                predicates.add(cb.equal(root.get("valutazione"), valutazione));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return libroRepository.findAll(spec, risolviSort(sort));
    }

    // Whitelist degli ordinamenti consentiti (default: ultimi aggiunti)
    private Sort risolviSort(String sort) {
        if (sort == null) {
            return Sort.by(Sort.Direction.DESC, "id");
        }
        return switch (sort) {
            case "titolo" -> Sort.by(Sort.Order.by("titolo").ignoreCase());
            case "autore" -> Sort.by(Sort.Order.by("autore").ignoreCase());
            case "anno" -> Sort.by(Sort.Direction.DESC, "annoPubblicazione");
            case "valutazione" -> Sort.by(Sort.Direction.DESC, "valutazione");
            case "dataFine" -> Sort.by(Sort.Direction.DESC, "dataFineLettura");
            default -> Sort.by(Sort.Direction.DESC, "id");
        };
    }

    public long countLibri() {
        return libroRepository.count();
    }

    public long countByStatoLettura(StatoLettura statoLettura) {
        return libroRepository.countByStatoLettura(statoLettura);
    }

    public long countByCategoria(Categoria categoria) {
        return libroRepository.countByCategoria(categoria);
    }

    public List<Libro> getUltimiLibri() {
        return libroRepository.findTop5ByOrderByIdDesc();
    }
}
