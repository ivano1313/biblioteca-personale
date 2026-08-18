package it.ivano.biblioteca.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Recupera i metadati di un libro da Open Library a partire dall'ISBN.
 * Documentazione: https://openlibrary.org/dev/docs/api/books
 */
@Service
public class OpenLibraryService {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @return mappa con titolo, autore, annoPubblicazione (stringa ISO) oppure null se non trovato
     */
    public Map<String, String> cercaPerIsbn(String isbn) {
        String isbnPulito = isbn.replaceAll("[^0-9Xx]", "");
        if (isbnPulito.isEmpty()) {
            return null;
        }
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://openlibrary.org/isbn/" + isbnPulito + ".json"))
                    .timeout(Duration.ofSeconds(8))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                return null;
            }

            JsonNode libro = objectMapper.readTree(response.body());
            Map<String, String> risultato = new HashMap<>();
            risultato.put("titolo", libro.path("title").asText(null));

            // Gli autori sono riferimenti (/authors/OL...A): risolvo il primo
            JsonNode authors = libro.path("authors");
            if (authors.isArray() && !authors.isEmpty()) {
                String autoreKey = authors.get(0).path("key").asText(null);
                if (autoreKey != null) {
                    risultato.put("autore", risolviNomeAutore(autoreKey));
                }
            }

            // publish_date è in formato libero ("29 July 1954", "1980", ...): estraggo l'anno
            String publishDate = libro.path("publish_date").asText("");
            String anno = estraiAnno(publishDate);
            if (anno != null) {
                risultato.put("annoPubblicazione", anno + "-01-01");
            }

            return risultato;
        } catch (Exception e) {
            return null;
        }
    }

    private String risolviNomeAutore(String autoreKey) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://openlibrary.org" + autoreKey + ".json"))
                    .timeout(Duration.ofSeconds(8))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return objectMapper.readTree(response.body()).path("name").asText(null);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private String estraiAnno(String publishDate) {
        java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(\\d{4})").matcher(publishDate);
        return matcher.find() ? matcher.group(1) : null;
    }
}
