package it.ivano.biblioteca.repository;

import it.ivano.biblioteca.model.Libro;
import it.ivano.biblioteca.model.Categoria;
import it.ivano.biblioteca.model.StatoLettura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Integer>, JpaSpecificationExecutor<Libro> {

    List<Libro> findByTitoloContainingIgnoreCase(String titolo);

    List<Libro> findByAutoreContainingIgnoreCase(String autore);

    List<Libro> findByIsbn(String isbn);

    List<Libro> findByCategoria(Categoria categoria);

    long countByStatoLettura(StatoLettura statoLettura);

    long countByCategoria(Categoria categoria);

    List<Libro> findTop5ByOrderByIdDesc();

    Optional<Libro> findFirstByStatoLetturaOrderByIdDesc(StatoLettura statoLettura);

    List<Libro> findByDataFineLetturaIsNotNull();

    long countByDataFineLetturaBetween(LocalDate inizio, LocalDate fine);
}
