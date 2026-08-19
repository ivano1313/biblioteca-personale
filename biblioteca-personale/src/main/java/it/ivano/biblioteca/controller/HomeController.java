package it.ivano.biblioteca.controller;

import it.ivano.biblioteca.model.Categoria;
import it.ivano.biblioteca.model.StatoLettura;
import it.ivano.biblioteca.service.LibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
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

        // Hero: il libro attualmente in lettura (se c'e')
        model.addAttribute("libroInLettura", libroService.getLibroInLettura());

        // Grafico: libri letti negli ultimi 12 mesi
        Map<YearMonth, Long> perMese = libroService.getLibriLettiPerMese();
        List<String> mesiLabels = new ArrayList<>();
        List<Long> mesiValori = new ArrayList<>();
        DateTimeFormatter formatoMese = DateTimeFormatter.ofPattern("MMM yy", Locale.ITALIAN);
        YearMonth mese = YearMonth.now().minusMonths(11);
        for (int i = 0; i < 12; i++) {
            mesiLabels.add(mese.format(formatoMese));
            mesiValori.add(perMese.getOrDefault(mese, 0L));
            mese = mese.plusMonths(1);
        }
        model.addAttribute("mesiLabels", mesiLabels);
        model.addAttribute("mesiValori", mesiValori);

        // Grafico: distribuzione per categoria (liste parallele per Chart.js)
        List<String> catLabels = new ArrayList<>();
        List<Long> catValori = new ArrayList<>();
        for (Map.Entry<Categoria, Long> entry : perCategoria.entrySet()) {
            catLabels.add(entry.getKey().name());
            catValori.add(entry.getValue());
        }
        model.addAttribute("catLabels", catLabels);
        model.addAttribute("catValori", catValori);

        return "home";
    }
}
