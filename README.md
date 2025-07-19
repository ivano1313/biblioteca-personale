# Biblioteca Personale 📚

Gestione di una biblioteca personale realizzata con **Spring Boot**.  
Permette di aggiungere, modificare, cercare ed eliminare libri con una semplice interfaccia web.

---

## 🚀 Funzionalità implementate

- ✅ Inserimento di nuovi libri
- ✅ Modifica dati libro esistente
- ✅ Eliminazione con conferma (modal Bootstrap)
- ✅ Visualizzazione lista completa
- ✅ Ricerca per titolo, autore, ISBN, categoria
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

Apri il browser su [http://localhost:8080/libri](http://localhost:8080/libri)

---

## 🧪 Console H2

- URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:./database/biblioteca`
- User: `sa` — Password: (vuota)

---

## 📂 Struttura del progetto

```
📁 model/
    └── Libro.java, Categoria.java
📁 repository/
    └── LibroRepository.java
📁 service/
    └── LibroService.java
📁 controller/
    └── LibroWebController.java, LibroRestController.java
📁 templates/
    └── libri.html, form.html
```

---

## 📌 Diagrammi UML inclusi

Sono disponibili nella cartella `/uml_diagrams`:

- 📘 Class Diagram
- 🔁 Sequence Diagram (aggiungi + elimina)
- 📦 Package Diagram

---

## 📝 Licenza

Questo progetto è distribuito sotto licenza MIT.