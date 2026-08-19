package it.ivano.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String nome;

    @ManyToMany(mappedBy = "tag")
    @JsonIgnore // evita ricorsione infinita nella serializzazione JSON
    private Set<Libro> libri = new HashSet<>();

    public Tag() {
        // Costruttore di default richiesto da JPA
    }

    public Tag(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Libro> getLibri() {
        return libri;
    }

    public void setLibri(Set<Libro> libri) {
        this.libri = libri;
    }
}
