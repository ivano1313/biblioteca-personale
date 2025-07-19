package it.ivano.biblioteca.repository;

import it.ivano.biblioteca.model.Libro;
import it.ivano.biblioteca.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Integer> {

    List<Libro> findByTitoloContainingIgnoreCase(String titolo);

    List<Libro> findByAutoreContainingIgnoreCase(String autore);

    List<Libro> findByIsbn(String isbn);

    List<Libro> findByCategoria(Categoria categoria);


}
