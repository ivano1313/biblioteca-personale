package it.ivano.biblioteca.model;

public enum Categoria {

    ROMANZI,
    SAGGI,
    FANTASY,
    SCIENZA,
    STORIA,
    BIOGRAFIE,
    POESIA,
    FUMETTI,
    GIALLO,
    HORROR,
    AVVENTURA,
    CLASSICI,
    TECNOLOGIA,
    ARTE,
    MUSICA,
    CUCINA,
    VIAGGI,
    INFANZIA,
    INFORMATICA,
    ALTRO; // Aggiunto per gestire fallback

    public static Categoria fromString(String value) {
        try {
            return Categoria.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return ALTRO;
        }
    }
}
