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

- `model/` — JPA entities and enums:
  - `Libro` — the book. Relations: `citazioni` (`@OneToMany`), `tag` (`@ManyToMany` via join table `libro_tag`). Derived getter `getPercentualeLettura()` (not persisted).
  - `Citazione` — a quote belonging to a book (`@ManyToOne`, `@JsonIgnore` on the back-reference).
  - `Tag` — free-form label, `@ManyToMany` with `Libro` (`@JsonIgnore` on the back-reference).
  - `Impostazione` — global key/value setting (e.g. `obiettivoAnnuale`).
  - Enums `Categoria`, `StatoLettura` (4 values incl. `ABBANDONATO`). `StatoLettura` carries an Italian `label` used for display in templates.
- `repository/` — `LibroRepository` (also `JpaSpecificationExecutor` for the filterable book list), `CitazioneRepository`, `TagRepository`, `ImpostazioneRepository`.
- `service/` — `LibroService` (CRUD, counts, `cercaLibri` with Specification filters, tag resolution, reading goal) and `OpenLibraryService` (HTTP calls to openlibrary.org for ISBN metadata lookup, using `java.net.http.HttpClient` + Jackson).
- `controller/` — three controllers with distinct roles:
  - `HomeController` → dashboard at `/` (statistics, hero "stai leggendo", Chart.js graphs, reading goal) and `POST /impostazioni/obiettivo`
  - `LibroWebController` → Thymeleaf pages under `/libri` (form-based, redirects after POST). Includes: book detail page `GET /libri/{id}`, inline progress update `POST /libri/{id}/progresso` (JSON, called via fetch), quotes `POST /libri/{id}/citazioni` and `/libri/{id}/citazioni/{cid}/elimina`
  - `LibroRestController` → JSON API under `/api/libri`
- `templates/` — Thymeleaf + Bootstrap 5 (CDN) + Bootstrap Icons + Chart.js (CDN, home only). No static build step, no JS framework. Templates: `home.html` (dashboard), `libri.html` (filterable grid/list), `form.html` (add/edit), `dettaglio.html` (book detail).

**Two REST APIs coexist:** the hand-written `LibroRestController` at `/api/libri` AND the auto-generated Spring Data REST endpoints (from `spring-boot-starter-data-rest`) which are remapped under `/api` via `spring.data.rest.base-path=/api` in `application.properties` (e.g. `/api/libroes`). Do not remove that property: without it Spring Data REST occupies `/` and breaks the home page.

**Persistence:** H2 file database at `./data/biblioteca-db` (relative to the working directory where the app runs), `ddl-auto=update`, console at `/h2-console` (user `sa`, empty password).

**Book covers** are not stored: templates load them directly from `https://covers.openlibrary.org/b/isbn/{isbn}-M.jpg` with a JS `onerror` fallback to a placeholder.

## Conventions

- **Language: Italian.** UI text, code comments, and git commit messages are in Italian. Keep it that way.
- **Lombok is a declared dependency but the codebase uses hand-written getters/setters** (see `Libro.java`). Follow the existing manual style unless deliberately migrating.
- **Enums are mapped as VARCHAR**, not native H2 ENUM: fields carry `@Enumerated(EnumType.STRING) @JdbcTypeCode(SqlTypes.VARCHAR)`. Without this, adding a value to an enum breaks the existing DB ("Value not permitted for column") and requires a manual `ALTER COLUMN ... SET DATA TYPE VARCHAR`.
- Web controller updates copy fields one-by-one onto the managed entity (see `aggiornaLibro` in `LibroWebController`) instead of saving the detached form object directly — preserve this pattern when adding fields to `Libro`, and remember to add the new field there too.
- When adding a field to `Libro`, update in sync: `form.html` (input), `libri.html` (card + list view), `dettaglio.html` (detail page), `LibroWebController.aggiornaLibro` (field copy), and both `mostraForm`/`modificaLibro` model attributes if it's an enum (templates expect `categorie` and `stati` attributes).
- **Form params must not collide with `Libro` collection properties**: the tags input is named `tagCsv` (not `tag`) because Spring would otherwise try to bind the string into `Set<Tag> Libro.tag` and fail with a 400.
- The book list filters (`/libri?titolo=&stato=&categoria=&valutazione=&tag=&sort=`) are cumulative and handled by `LibroService.cercaLibri` with a JPA `Specification`; sort keys are whitelisted in `risolviSort`.
- The grid/list toggle in `libri.html` is pure JS with the preference in `localStorage` (`vistaLibri`); delete modals live outside the two view containers so both can trigger them.
- Templates reference Bootstrap/Bootstrap Icons/Chart.js from jsDelivr CDN — no npm, no local assets.
- `Libro.annoPubblicazione` is a `LocalDate` (not a year integer); the form binds it via `<input type="date">`. Same for `dataInizioLettura`/`dataFineLettura`.
- Session/sprint closure rule: at the end of every session and sprint, update docs (README + roadmap in `report-app-biblioteche.md`), commit and push. See the `chiusura-sessione` skill.
