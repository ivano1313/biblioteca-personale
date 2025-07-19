package it.ivano.biblioteca.controller;

import it.ivano.biblioteca.model.Categoria;
import it.ivano.biblioteca.model.Libro;
import it.ivano.biblioteca.service.LibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class LibroWebController {

    private final LibroService libroService;

    public LibroWebController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping("/libri")
    public String getLibriPage(Model model) {
        model.addAttribute("libri", libroService.getAllLibri());
        return "libri";
    }

    @GetMapping("/libri/modifica/{id}")
    public String modificaLibro(@PathVariable("id") Integer id, Model model) {
        Libro libro = libroService.getLibroById(id);
        if (libro != null) {
            model.addAttribute("libro", libro);
            model.addAttribute("categorie", Categoria.values()); // Aggiungi le categorie disponibili
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
            libroEsistente.setCategoria(libro.getCategoria());
            libroEsistente.setAnnoPubblicazione(libro.getAnnoPubblicazione());
            libroService.updateLibro(id, libroEsistente);
        }
        return "redirect:/libri";
    }

    @GetMapping("/libri/nuovo")
    public String mostraForm(Model model){
        model.addAttribute("libro", new Libro());
        model.addAttribute("categorie", Categoria.values()); // Aggiungi le categorie disponibili
        return "form";
    }

    @PostMapping("/libri/add")
    public String aggiungiLibro(@ModelAttribute("libro") Libro libro) {
        libroService.addLibro(libro);
        return "redirect:/libri";
    }

    // LibroWebController.java
    @PostMapping("/libri/elimina/{id}")
    public String eliminaLibro(@PathVariable Integer id) {
        libroService.deleteLibro(id);          // delega al service
        return "redirect:/libri";              // torni subito alla lista
    }


}
