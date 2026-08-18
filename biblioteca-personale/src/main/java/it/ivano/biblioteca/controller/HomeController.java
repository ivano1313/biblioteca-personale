package it.ivano.biblioteca.controller;

import it.ivano.biblioteca.model.Categoria;
import it.ivano.biblioteca.model.StatoLettura;
import it.ivano.biblioteca.service.LibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class HomeController {

    private final LibroService libroService;

    public HomeController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totaleLibri", libroService.countLibri());

        Map<StatoLettura, Long> perStato = new LinkedHashMap<>();
        for (StatoLettura stato : StatoLettura.values()) {
            perStato.put(stato, libroService.countByStatoLettura(stato));
        }
        model.addAttribute("perStato", perStato);

        Map<Categoria, Long> perCategoria = new LinkedHashMap<>();
        for (Categoria categoria : Categoria.values()) {
            long n = libroService.countByCategoria(categoria);
            if (n > 0) {
                perCategoria.put(categoria, n);
            }
        }
        model.addAttribute("perCategoria", perCategoria);

        model.addAttribute("ultimiLibri", libroService.getUltimiLibri());
        return "home";
    }
}
