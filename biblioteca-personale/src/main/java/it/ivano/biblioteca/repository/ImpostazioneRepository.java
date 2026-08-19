package it.ivano.biblioteca.repository;

import it.ivano.biblioteca.model.Impostazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImpostazioneRepository extends JpaRepository<Impostazione, String> {
}
