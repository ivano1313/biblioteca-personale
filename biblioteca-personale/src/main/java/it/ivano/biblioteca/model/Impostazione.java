package it.ivano.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// Coppia chiave/valore per le impostazioni globali dell'app (es. obiettivo annuale)
@Entity
public class Impostazione {

    @Id
    private String chiave;

    private String valore;

    public Impostazione() {
        // Costruttore di default richiesto da JPA
    }

    public Impostazione(String chiave, String valore) {
        this.chiave = chiave;
        this.valore = valore;
    }

    public String getChiave() {
        return chiave;
    }

    public void setChiave(String chiave) {
        this.chiave = chiave;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }
}
