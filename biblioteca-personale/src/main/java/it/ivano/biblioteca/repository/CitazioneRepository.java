package it.ivano.biblioteca.repository;

import it.ivano.biblioteca.model.Citazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitazioneRepository extends JpaRepository<Citazione, Integer> {
}
