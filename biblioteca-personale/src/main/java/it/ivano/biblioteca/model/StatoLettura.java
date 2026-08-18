package it.ivano.biblioteca.model;

public enum StatoLettura {

    DA_LEGGERE("Da leggere"),
    IN_LETTURA("In lettura"),
    LETTO("Letto");

    private final String label;

    StatoLettura(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
