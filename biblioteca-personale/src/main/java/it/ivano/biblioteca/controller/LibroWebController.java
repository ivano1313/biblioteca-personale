package it.ivano.biblioteca.controller;

import it.ivano.biblioteca.model.Categoria;
import it.ivano.biblioteca.model.Libro;
import it.ivano.biblioteca.model.StatoLettura;
import it.ivano.biblioteca.model.Tag;
import it.ivano.biblioteca.service.LibroService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class LibroWebController {

    private final LibroService libroService;

    public LibroWebController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping("/libri")
    public String getLibriPage(Model model,
                               @RequestParam(required = false) String titolo,
                               @RequestParam(required = false) StatoLettura stato,
                               @RequestParam(required = false) Categoria categoria,
                               @RequestParam(required = false) Integer valutazione,
                               @RequestParam(required = false) String tag,
                               @RequestParam(required = false) String sort) {
        model.addAttribute("libri", libroService.cercaLibri(titolo, stato, categoria, valutazione, tag, sort));
        model.addAttribute("categorie", Categoria.values());
        model.addAttribute("stati", StatoLettura.values());
        model.addAttribute("tuttiITag", libroService.getAllTag());
        // Valori correnti dei filtri, per mantenere le select sincronizzate
        model.addAttribute("titoloCorrente", titolo);
        model.addAttribute("statoCorrente", stato);
        model.addAttribute("categoriaCorrente", categoria);
        model.addAttribute("valutazioneCorrente", valutazione);
        model.addAttribute("tagCorrente", tag);
        model.addAttribute("sortCorrente", sort);
        return "libri";
    }

    @GetMapping("/libri/{id}")
    public String dettaglioLibro(@PathVariable("id") Integer id, Model model) {
        Libro libro = libroService.getLibroById(id);
        if (libro == null) {
            return "redirect:/libri";
        }
        model.addAttribute("libro", libro);
        return "dettaglio";
    }

    @GetMapping("/libri/modifica/{id}")
    public String modificaLibro(@PathVariable("id") Integer id, Model model) {
        Libro libro = libroService.getLibroById(id);
        if (libro != null) {
            model.addAttribute("libro", libro);
            model.addAttribute("categorie", Categoria.values()); // Aggiungi le categorie disponibili
            model.addAttribute("stati", StatoLettura.values()); // Stati di lettura disponibili
            return "form";
        }
        return "redirect:/libri";
    }

    @PostMapping("/libri/modifica/{id}")
    public String aggiornaLibro(@PathVariable("id") Integer id, @ModelAttribute("libro") Libro libro,
                                @RequestParam(required = false) String tagCsv) {
        Libro libroEsistente = libroService.getLibroById(id);
        if (libroEsistente != null) {
            libroEsistente.setTitolo(libro.getTitolo());
            libroEsistente.setAutore(libro.getAutore());
            libroEsistente.setIsbn(libro.getIsbn());
            libroEsistente.setCategoria(libro.getCategoria());
            libroEsistente.setAnnoPubblicazione(libro.getAnnoPubblicazione());
            libroEsistente.setStatoLettura(libro.getStatoLettura());
            libroEsistente.setValutazione(libro.getValutazione());
            libroEsistente.setNote(libro.getNote());
            libroEsistente.setDataInizioLettura(libro.getDataInizioLettura());
            libroEsistente.setDataFineLettura(libro.getDataFineLettura());
            libroEsistente.setPagineAttuali(libro.getPagineAttuali());
            libroEsistente.setTotalePagine(libro.getTotalePagine());
            libroEsistente.setTag(libroService.risolviTag(tagCsv));
            libroService.updateLibro(id, libroEsistente);
            libroService.eliminaTagOrfani();
        }
        return "redirect:/libri/" + id;
    }

    @GetMapping("/libri/nuovo")
    public String mostraForm(Model model){
        model.addAttribute("libro", new Libro());
        model.addAttribute("categorie", Categoria.values()); // Aggiungi le categorie disponibili
        model.addAttribute("stati", StatoLettura.values()); // Stati di lettura disponibili
        return "form";
    }

    @PostMapping("/libri/add")
    public String aggiungiLibro(@ModelAttribute("libro") Libro libro,
                                @RequestParam(required = false) String tagCsv) {
        libro.setTag(libroService.risolviTag(tagCsv));
        libroService.addLibro(libro);
        return "redirect:/libri";
    }

    // Aggiornamento rapido del progresso pagine (chiamato via fetch dalla UI)
    @PostMapping("/libri/{id}/progresso")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> aggiornaProgresso(@PathVariable Integer id,
                                                                 @RequestParam Integer pagineAttuali) {
        Libro libro = libroService.aggiornaProgresso(id, pagineAttuali);
        if (libro == null) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> risposta = new HashMap<>();
        risposta.put("pagineAttuali", libro.getPagineAttuali());
        risposta.put("totalePagine", libro.getTotalePagine());
        risposta.put("percentuale", libro.getPercentualeLettura());
        return ResponseEntity.ok(risposta);
    }

    @PostMapping("/libri/{id}/citazioni")
    public String aggiungiCitazione(@PathVariable Integer id,
                                    @RequestParam String testo,
                                    @RequestParam(required = false) Integer pagina) {
        libroService.aggiungiCitazione(id, testo, pagina);
        return "redirect:/libri/" + id;
    }

    @PostMapping("/libri/{id}/citazioni/{citazioneId}/elimina")
    public String eliminaCitazione(@PathVariable Integer id, @PathVariable Integer citazioneId) {
        libroService.eliminaCitazione(citazioneId);
        return "redirect:/libri/" + id;
    }

    @PostMapping("/libri/elimina/{id}")
    public String eliminaLibro(@PathVariable Integer id) {
        libroService.deleteLibro(id);
        libroService.eliminaTagOrfani();
        return "redirect:/libri";
    }

    // Export CSV della libreria: accetta gli stessi filtri della lista, così l'export riflette la vista corrente.
    // Separatore ";" e BOM UTF-8 per una corretta apertura in Excel con locale italiano.
    @GetMapping("/libri/export")
    public ResponseEntity<byte[]> esportaCsv(@RequestParam(required = false) String titolo,
                                             @RequestParam(required = false) StatoLettura stato,
                                             @RequestParam(required = false) Categoria categoria,
                                             @RequestParam(required = false) Integer valutazione,
                                             @RequestParam(required = false) String tag,
                                             @RequestParam(required = false) String sort) {
        List<Libro> libri = libroService.cercaLibri(titolo, stato, categoria, valutazione, tag, sort);
        StringBuilder csv = new StringBuilder("\uFEFF"); // BOM per Excel
        csv.append("Titolo;Autore;ISBN;Categoria;Stato;Valutazione;Pagine lette;Pagine totali;% lettura;")
           .append("Anno pubblicazione;Inizio lettura;Fine lettura;Tag;Note\n");
        for (Libro l : libri) {
            csv.append(escCsv(l.getTitolo())).append(';')
               .append(escCsv(l.getAutore())).append(';')
               .append(escCsv(l.getIsbn())).append(';')
               .append(l.getCategoria() != null ? l.getCategoria().name() : "").append(';')
               .append(l.getStatoLettura() != null ? escCsv(l.getStatoLettura().getLabel()) : "").append(';')
               .append(l.getValutazione() != null ? l.getValutazione() : "").append(';')
               .append(l.getPagineAttuali() != null ? l.getPagineAttuali() : "").append(';')
               .append(l.getTotalePagine() != null ? l.getTotalePagine() : "").append(';')
               .append(l.getPercentualeLettura() != null ? l.getPercentualeLettura() : "").append(';')
               .append(l.getAnnoPubblicazione() != null ? l.getAnnoPubblicazione() : "").append(';')
               .append(l.getDataInizioLettura() != null ? l.getDataInizioLettura() : "").append(';')
               .append(l.getDataFineLettura() != null ? l.getDataFineLettura() : "").append(';')
               .append(escCsv(l.getTag().stream().map(Tag::getNome).sorted().collect(Collectors.joining(", ")))).append(';')
               .append(escCsv(l.getNote())).append('\n');
        }
        byte[] bytes = csv.toString().getBytes(StandardCharsets.UTF_8);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"biblioteca.csv\"")
                .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
                .body(bytes);
    }

    // Racchiude tra doppi apici i campi che contengono separatore, apici o newline (raddoppiando gli apici interni)
    private String escCsv(String valore) {
        if (valore == null) {
            return "";
        }
        if (valore.contains(";") || valore.contains("\"") || valore.contains("\n") || valore.contains("\r")) {
            return '"' + valore.replace("\"", "\"\"") + '"';
        }
        return valore;
    }

}
