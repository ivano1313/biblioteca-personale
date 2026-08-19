package it.ivano.biblioteca.service;

import it.ivano.biblioteca.model.Categoria;
import it.ivano.biblioteca.model.Citazione;
import it.ivano.biblioteca.model.Impostazione;
import it.ivano.biblioteca.model.Libro;
import it.ivano.biblioteca.model.StatoLettura;
import it.ivano.biblioteca.model.Tag;
import it.ivano.biblioteca.repository.CitazioneRepository;
import it.ivano.biblioteca.repository.ImpostazioneRepository;
import it.ivano.biblioteca.repository.LibroRepository;
import it.ivano.biblioteca.repository.TagRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LibroService {

    private static final String CHIAVE_OBIETTIVO_ANNUALE = "obiettivoAnnuale";

    private final LibroRepository libroRepository;
    private final CitazioneRepository citazioneRepository;
    private final ImpostazioneRepository impostazioneRepository;
    private final TagRepository tagRepository;

    public LibroService(LibroRepository libroRepository, CitazioneRepository citazioneRepository,
                        ImpostazioneRepository impostazioneRepository, TagRepository tagRepository) {
        this.libroRepository = libroRepository;
        this.citazioneRepository = citazioneRepository;
        this.impostazioneRepository = impostazioneRepository;
        this.tagRepository = tagRepository;
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

    // Ricerca con filtri cumulabili (titolo, stato, categoria, valutazione, tag) e ordinamento
    public List<Libro> cercaLibri(String titolo, StatoLettura stato, Categoria categoria, Integer valutazione,
                                  String tag, String sort) {
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
            if (tag != null && !tag.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.join("tag").get("nome")), tag.toLowerCase()));
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

    // Il libro in lettura piu' recente (per la hero card in dashboard)
    public Libro getLibroInLettura() {
        return libroRepository.findFirstByStatoLetturaOrderByIdDesc(StatoLettura.IN_LETTURA).orElse(null);
    }

    // Aggiorna solo il progresso pagine; ritorna null se il libro non esiste
    public Libro aggiornaProgresso(Integer id, Integer pagineAttuali) {
        Libro libro = getLibroById(id);
        if (libro == null) {
            return null;
        }
        libro.setPagineAttuali(pagineAttuali);
        return libroRepository.save(libro);
    }

    // Libri letti raggruppati per mese di fine lettura (chiave: "2026-08")
    public Map<YearMonth, Long> getLibriLettiPerMese() {
        return libroRepository.findByDataFineLetturaIsNotNull().stream()
                .collect(Collectors.groupingBy(
                        libro -> YearMonth.from(libro.getDataFineLettura()),
                        Collectors.counting()));
    }

    // Aggiunge una citazione a un libro; ritorna null se il libro non esiste o il testo e' vuoto
    public Citazione aggiungiCitazione(Integer libroId, String testo, Integer pagina) {
        Libro libro = getLibroById(libroId);
        if (libro == null || testo == null || testo.isBlank()) {
            return null;
        }
        Citazione citazione = new Citazione();
        citazione.setTesto(testo.trim());
        citazione.setPagina(pagina);
        citazione.setLibro(libro);
        return citazioneRepository.save(citazione);
    }

    public void eliminaCitazione(Integer citazioneId) {
        citazioneRepository.deleteById(citazioneId);
    }

    // Obiettivo di lettura per l'anno corrente (null = non impostato)
    public Integer getObiettivoAnnuale() {
        return impostazioneRepository.findById(CHIAVE_OBIETTIVO_ANNUALE)
                .map(impostazione -> Integer.valueOf(impostazione.getValore()))
                .orElse(null);
    }

    public void setObiettivoAnnuale(Integer obiettivo) {
        impostazioneRepository.save(new Impostazione(CHIAVE_OBIETTIVO_ANNUALE, String.valueOf(obiettivo)));
    }

    // Libri finiti nell'anno indicato (in base alla data di fine lettura)
    public long countLettiNellAnno(int anno) {
        return libroRepository.countByDataFineLetturaBetween(
                LocalDate.of(anno, 1, 1), LocalDate.of(anno, 12, 31));
    }

    // Risolve una stringa CSV di nomi in entita Tag esistenti o nuove
    public Set<Tag> risolviTag(String tagCsv) {
        Set<Tag> risultato = new HashSet<>();
        if (tagCsv == null || tagCsv.isBlank()) {
            return risultato;
        }
        for (String nome : tagCsv.split(",")) {
            String pulito = nome.trim();
            if (!pulito.isEmpty()) {
                risultato.add(tagRepository.findByNomeIgnoreCase(pulito)
                        .orElseGet(() -> tagRepository.save(new Tag(pulito))));
            }
        }
        return risultato;
    }

    public List<Tag> getAllTag() {
        return tagRepository.findAll(Sort.by("nome"));
    }

    // Rimuove i tag rimasti senza libri (dopo eliminazioni o modifiche)
    public void eliminaTagOrfani() {
        tagRepository.deleteAll(tagRepository.findByLibriIsEmpty());
    }
}
