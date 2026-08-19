package it.ivano.biblioteca.repository;

import it.ivano.biblioteca.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findByNomeIgnoreCase(String nome);

    // Tag non associati a nessun libro (da ripulire)
    List<Tag> findByLibriIsEmpty();
}
