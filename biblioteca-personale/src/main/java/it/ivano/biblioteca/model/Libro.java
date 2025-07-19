package it.ivano.biblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titolo;
    private String autore;
    private String isbn;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private LocalDate annoPubblicazione;

    public Libro(Integer id, String titolo, String autore, String isbn, LocalDate annoPubblicazione, Categoria categoria) {
        this.id = id;
        this.titolo = titolo;
        this.autore = autore;
        this.isbn = isbn;
        this.annoPubblicazione = annoPubblicazione;
        this.categoria = categoria;
    }

    public Libro() {
        // Costruttore di default richiesto da JPA
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public LocalDate getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public void setAnnoPubblicazione(LocalDate annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titolo='" + titolo + '\'' +
                ", autore='" + autore + '\'' +
                ", isbn='" + isbn + '\'' +
                ", categoria=" + categoria +
                ", annoPubblicazione=" + annoPubblicazione +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        if (!titolo.equals(libro.titolo)) return false;
        if (!autore.equals(libro.autore)) return false;
        if (!isbn.equals(libro.isbn)) return false;
        if (!categoria.equals(libro.categoria)) return false;
        return annoPubblicazione.equals(libro.annoPubblicazione);
    }

    @Override
    public int hashCode() {
        int result = titolo.hashCode();
        result = 31 * result + autore.hashCode();
        result = 31 * result + isbn.hashCode();
        result = 31 * result + categoria.hashCode();
        result = 31 * result + annoPubblicazione.hashCode();
        return result;
    }
}
