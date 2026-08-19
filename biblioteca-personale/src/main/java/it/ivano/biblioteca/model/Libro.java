package it.ivano.biblioteca.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titolo;
    private String autore;
    private String isbn;

    // VARCHAR (non ENUM nativo H2): aggiungere valori all'enum non richiede migration
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private Categoria categoria;

    private LocalDate annoPubblicazione;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private StatoLettura statoLettura = StatoLettura.DA_LEGGERE;

    // Valutazione da 1 a 5 stelle (null = non valutato)
    private Integer valutazione;

    @Column(length = 2000)
    private String note;

    // Date di lettura (null = non tracciata)
    private LocalDate dataInizioLettura;
    private LocalDate dataFineLettura;

    // Progresso di lettura in pagine (null = non tracciato)
    private Integer pagineAttuali;
    private Integer totalePagine;

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

    public StatoLettura getStatoLettura() {
        return statoLettura;
    }

    public void setStatoLettura(StatoLettura statoLettura) {
        this.statoLettura = statoLettura;
    }

    public Integer getValutazione() {
        return valutazione;
    }

    public void setValutazione(Integer valutazione) {
        this.valutazione = valutazione;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDate getDataInizioLettura() {
        return dataInizioLettura;
    }

    public void setDataInizioLettura(LocalDate dataInizioLettura) {
        this.dataInizioLettura = dataInizioLettura;
    }

    public LocalDate getDataFineLettura() {
        return dataFineLettura;
    }

    public void setDataFineLettura(LocalDate dataFineLettura) {
        this.dataFineLettura = dataFineLettura;
    }

    public Integer getPagineAttuali() {
        return pagineAttuali;
    }

    public void setPagineAttuali(Integer pagineAttuali) {
        this.pagineAttuali = pagineAttuali;
    }

    public Integer getTotalePagine() {
        return totalePagine;
    }

    public void setTotalePagine(Integer totalePagine) {
        this.totalePagine = totalePagine;
    }

    // Percentuale di completamento 0-100, null se il totale non e' noto
    public Integer getPercentualeLettura() {
        if (pagineAttuali == null || totalePagine == null || totalePagine <= 0) {
            return null;
        }
        return Math.min(100, pagineAttuali * 100 / totalePagine);
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
