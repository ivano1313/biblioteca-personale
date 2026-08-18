# Copilot Instructions — Biblioteca Personale

## Project layout

The git repository root contains the actual Maven project in the **`biblioteca-personale/` subdirectory** (not at the root). All build/run commands must be executed from `biblioteca-personale/`.

## Build, run, and test

All commands use the Maven wrapper from `biblioteca-personale/`:

```bash
./mvnw compile                          # compile
./mvnw spring-boot:run                  # run the app on http://localhost:8080
./mvnw test                             # run all tests
./mvnw test -Dtest=NomeDelTest          # run a single test class
./mvnw test -Dtest=NomeDelTest#metodo   # run a single test method
```

- No linter/formatter is configured; there is no lint command.
- `spring-boot-devtools` is on the classpath: while the app is running via `spring-boot:run`, executing `./mvnw compile` in a second terminal triggers an automatic restart with the new code.
- Java 21, Spring Boot 3.5.3.

## Architecture

Classic layered Spring Boot monolith (package `it.ivano.biblioteca`):

- `model/` — JPA entities (`Libro`) and enums (`Categoria`, `StatoLettura`). Enums carry an Italian `label` used for display in templates.
- `repository/` — `LibroRepository` (Spring Data JPA, derived query methods only).
- `service/` — `LibroService` (CRUD + counts) and `OpenLibraryService` (HTTP calls to openlibrary.org for ISBN metadata lookup, using `java.net.http.HttpClient` + Jackson).
- `controller/` — three controllers with distinct roles:
  - `HomeController` → dashboard at `/` (statistics, recent books)
  - `LibroWebController` → Thymeleaf pages under `/libri` (form-based, redirects after POST)
  - `LibroRestController` → JSON API under `/api/libri`
- `templates/` — Thymeleaf + Bootstrap 5 (CDN) + Bootstrap Icons. No static build step, no JS framework.

**Two REST APIs coexist:** the hand-written `LibroRestController` at `/api/libri` AND the auto-generated Spring Data REST endpoints (from `spring-boot-starter-data-rest`) which are remapped under `/api` via `spring.data.rest.base-path=/api` in `application.properties` (e.g. `/api/libroes`). Do not remove that property: without it Spring Data REST occupies `/` and breaks the home page.

**Persistence:** H2 file database at `./data/biblioteca-db` (relative to the working directory where the app runs), `ddl-auto=update`, console at `/h2-console` (user `sa`, empty password).

**Book covers** are not stored: templates load them directly from `https://covers.openlibrary.org/b/isbn/{isbn}-M.jpg` with a JS `onerror` fallback to a placeholder.

## Conventions

- **Language: Italian.** UI text, code comments, and git commit messages are in Italian. Keep it that way.
- **Lombok is a declared dependency but the codebase uses hand-written getters/setters** (see `Libro.java`). Follow the existing manual style unless deliberately migrating.
- Web controller updates copy fields one-by-one onto the managed entity (see `aggiornaLibro` in `LibroWebController`) instead of saving the detached form object directly — preserve this pattern when adding fields to `Libro`, and remember to add the new field there too.
- When adding a field to `Libro`, update in sync: `form.html` (input), `libri.html` (card display), `LibroWebController.aggiornaLibro` (field copy), and both `mostraForm`/`modificaLibro` model attributes if it's an enum (templates expect `categorie` and `stati` attributes).
- Templates reference Bootstrap/Bootstrap Icons from jsDelivr CDN — no npm, no local assets.
- `Libro.annoPubblicazione` is a `LocalDate` (not a year integer); the form binds it via `<input type="date">`.
