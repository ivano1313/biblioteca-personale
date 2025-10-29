package it.ivano.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Il titolo è obbligatorio")
    @Size(max = 255, message = "Il titolo non può superare 255 caratteri")
    private String titolo;

    @NotBlank(message = "L'autore è obbligatorio")
    @Size(max = 255, message = "L'autore non può superare 255 caratteri")
    private String autore;

    @Pattern(regexp = "^(97(8|9))?\\d{9}(\\d|X)$", message = "ISBN non valido")
    private String isbn;

    @NotNull(message = "La categoria è obbligatoria")
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @PastOrPresent(message = "L'anno di pubblicazione non può essere nel futuro")
    private LocalDate annoPubblicazione;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(titolo, libro.titolo) &&
               Objects.equals(autore, libro.autore) &&
               Objects.equals(isbn, libro.isbn) &&
               Objects.equals(categoria, libro.categoria) &&
               Objects.equals(annoPubblicazione, libro.annoPubblicazione);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titolo, autore, isbn, categoria, annoPubblicazione);
    }
}
