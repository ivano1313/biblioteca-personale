# Biblioteca Personale 📚

Applicazione full-stack per la gestione di una biblioteca personale, sviluppata con **Spring Boot**.
Consente di inserire, modificare, eliminare e visualizzare libri in base a titolo, autore, categoria e ISBN.

---

## 🚀 Funzionalità principali

- ✅ Aggiunta, modifica ed eliminazione libri
- 📋 Visualizzazione elenco libri (Thymeleaf + Bootstrap)
- 🔎 Ricerca per autore, titolo, ISBN e categoria (REST API)
- 💾 Salvataggio su database H2 (persistence file-based)
- 🔄 Integrazione backend con API REST e frontend Thymeleaf
- 📊 Diagrammi UML generati con PlantUML

---

## 🛠️ Tecnologie utilizzate

- **Java 17+**
- **Spring Boot** (MVC, Data JPA)
- **Thymeleaf** (view layer)
- **H2 Database** (embedded, console attiva)
- **Bootstrap 5** (frontend)
- **PlantUML** (documentazione tecnica)

---

## 🧪 Come eseguire il progetto

```bash
# Clona il progetto
https://github.com/ivano1313/biblioteca-personale.git

# Vai nella cartella
cd biblioteca-personale

# Compila ed esegui
./mvnw spring-boot:run
```

🔗 Visita l’app:
- Web: `http://localhost:8080/libri`
- Console H2: `http://localhost:8080/h2-console`

---

## 🧭 Diagrammi UML

Nel progetto sono presenti i seguenti file `.puml` (PlantUML):

- `biblioteca-class-diagram.puml` → struttura classi (MVC)
- `aggiungi-libro-sequence.puml` → sequenza aggiunta libro
- `elimina-libro-sequence.puml` → sequenza eliminazione libro
- `package-diagram.puml` → organizzazione a pacchetti

Puoi visualizzarli con:
```bash
java -jar plantuml.jar *.puml
```

---

## 👤 Autore

> Sviluppato da **Ivan Avallone**  
> GitHub: [ivano1313](https://github.com/ivano1313)

---

## 📄 Licenza

Questo progetto è open-source sotto licenza MIT. Può essere liberamente studiato, modificato e riutilizzato per fini personali e didattici.

---

> ✨ Progetto creato come esercizio completo per imparare Spring Boot con architettura pulita e approccio full-stack.
