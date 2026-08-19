package it.ivano.biblioteca.controller;

import it.ivano.biblioteca.model.Categoria;
import it.ivano.biblioteca.model.Libro;
import it.ivano.biblioteca.model.StatoLettura;
import it.ivano.biblioteca.service.LibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
                               @RequestParam(required = false) String sort) {
        model.addAttribute("libri", libroService.cercaLibri(titolo, stato, categoria, valutazione, sort));
        model.addAttribute("categorie", Categoria.values());
        model.addAttribute("stati", StatoLettura.values());
        // Valori correnti dei filtri, per mantenere le select sincronizzate
        model.addAttribute("titoloCorrente", titolo);
        model.addAttribute("statoCorrente", stato);
        model.addAttribute("categoriaCorrente", categoria);
        model.addAttribute("valutazioneCorrente", valutazione);
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
    public String aggiornaLibro(@PathVariable("id") Integer id, @ModelAttribute("libro") Libro libro) {
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
            libroService.updateLibro(id, libroEsistente);
        }
        return "redirect:/libri";
    }

    @GetMapping("/libri/nuovo")
    public String mostraForm(Model model){
        model.addAttribute("libro", new Libro());
        model.addAttribute("categorie", Categoria.values()); // Aggiungi le categorie disponibili
        model.addAttribute("stati", StatoLettura.values()); // Stati di lettura disponibili
        return "form";
    }

    @PostMapping("/libri/add")
    public String aggiungiLibro(@ModelAttribute("libro") Libro libro) {
        libroService.addLibro(libro);
        return "redirect:/libri";
    }

    @PostMapping("/libri/elimina/{id}")
    public String eliminaLibro(@PathVariable Integer id) {
        libroService.deleteLibro(id);
        return "redirect:/libri";
    }

}
