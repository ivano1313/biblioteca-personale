# Biblioteca Personale 📚

Gestione di una biblioteca personale realizzata con **Spring Boot**.  
Permette di aggiungere, modificare, cercare ed eliminare libri con una semplice interfaccia web.

---

## 🚀 Funzionalità implementate

- ✅ Dashboard iniziale con statistiche (libri per stato, per categoria, ultimi arrivi)
- ✅ Inserimento di nuovi libri
- ✅ Lookup automatico dei metadati da ISBN tramite Open Library
- ✅ Copertine caricate da Open Library (con placeholder di riserva)
- ✅ Stato di lettura (da leggere, in lettura, letto, abbandonato) e valutazione a stelle
- ✅ Date di inizio/fine lettura con durata calcolata
- ✅ Tracciamento progresso in pagine con barra di avanzamento (aggiornabile inline)
- ✅ Hero "Stai leggendo" in dashboard con il libro in corso
- ✅ Grafici in dashboard con Chart.js (libri letti per mese, distribuzione per categoria)
- ✅ Vista lista/griglia commutabile con preferenza salvata
- ✅ Citazioni per libro con pagina di riferimento (nella pagina dettaglio)
- ✅ Obiettivo di lettura annuale con barra di avanzamento in dashboard
- ✅ Tag personalizzati (badge, filtro dedicato, creazione al volo dal form)
- ✅ Pagina di dettaglio dedicata per ogni libro (`/libri/{id}`)
- ✅ Note personali su ogni libro
- ✅ Modifica dati libro esistente
- ✅ Eliminazione con conferma (modal Bootstrap)
- ✅ Visualizzazione lista completa
- ✅ Ricerca per titolo con filtri cumulabili (stato, categoria, valutazione) e ordinamento (titolo, autore, anno, valutazione, data fine lettura)
- ✅ API REST completa e testabile
- ✅ Frontend con Thymeleaf + Bootstrap 5
- ✅ Database H2 con console attiva

---

## 🔧 Tecnologie utilizzate

- Java 21
- Spring Boot 3
- Spring MVC
- Spring Data JPA
- Thymeleaf
- Bootstrap 5
- H2 Database

---

## ▶️ Come eseguire il progetto

```bash
# 1. Clona il progetto
git clone https://github.com/ivano1313/biblioteca-personale.git

# 2. Entra nella cartella
cd biblioteca-personale

# 3. Avvia l'app
./mvnw spring-boot:run
```

Apri il browser su [http://localhost:8080](http://localhost:8080) (dashboard) oppure [http://localhost:8080/libri](http://localhost:8080/libri) (lista libri).

---

## 🧪 Console H2

- URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:file:./data/biblioteca-db`
- User: `sa` — Password: (vuota)

---

## 📂 Struttura del progetto

```
📁 model/
    └── Libro.java, Categoria.java, StatoLettura.java, Citazione.java, Tag.java, Impostazione.java
📁 repository/
    └── LibroRepository.java, CitazioneRepository.java, TagRepository.java, ImpostazioneRepository.java
📁 service/
    └── LibroService.java, OpenLibraryService.java
📁 controller/
    └── HomeController.java, LibroWebController.java, LibroRestController.java
📁 templates/
    └── home.html, libri.html, form.html, dettaglio.html
```

---

## 🔌 API REST

- API personalizzata: `/api/libri` (CRUD + `/api/libri/lookup?isbn=...`)
- API generata da Spring Data REST: `/api/libroes`

---

## 📝 Licenza

Questo progetto è distribuito sotto licenza MIT.