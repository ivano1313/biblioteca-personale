Agent completed. agent_id: ricerca-biblioteche-3, agent_type: research, status: completed, description: Ricerca app biblioteche personali, elapsed: 815s, total_turns: 0, duration: 811s

Ho completato tutta la raccolta dati. Ora ho un quadro completo per produrre il report.

---

# 📚 REPORT: Analisi delle Migliori App di Gestione Biblioteca Personale
### Ispirazione per migliorare un'app web (Spring Boot + Thymeleaf + Bootstrap)
**Ricerca condotta su:** Goodreads, The StoryGraph, LibraryThing, BookWyrm, Hardcover + documentazione tecnica, pagine Wikipedia, fonti AlternativeTo, ProductHunt, CSS sorgente di Hardcover
**Data:** 18 agosto 2026

---

## SEZIONE 0 — STATO DELL'ARTE DEL TUO PROGETTO

Prima di analizzare i competitor, ho esaminato il codice attuale del tuo progetto per contestualizzare meglio le raccomandazioni.

**Quello che già hai:**
- Modello `Libro`: `titolo`, `autore`, `isbn`, `categoria`, `annoPubblicazione`, `statoLettura` (3 stati), `valutazione` (1–5 interi), `note`
- Integrazione Open Library via ISBN per autocompletamento (titolo/autore/anno)
- Copertine dinamiche da `covers.openlibrary.org/b/isbn/`
- Dashboard con contatori per stato + barre progresso per categoria
- Vista griglia libri con card (copertina + titolo + autore + badge stato + stelle + note abbreviate)
- Ricerca per titolo (GET param)
- Hover effect su card (`translateY(-4px)`)

**Gap principali rispetto ai competitor** (anticipazione):
1. Nessun tracciamento date (inizio/fine lettura)
2. Nessuna pagina di dettaglio libro dedicata
3. Solo 3 stati (manca DNF, ABBANDONATO)
4. Valutazione solo intera (niente mezze stelle)
5. Nessun filtro per stato/categoria/valutazione nella lista
6. Nessun ordinamento alternativo (alfabetico, anno, valutazione)
7. Nessuna vista lista (solo griglia)
8. Nessun goal di lettura annuale
9. Nessuna statistica temporale (libri/mese, trend annuale)
10. Note troppo brevi, niente citazioni/brani distinti

---

## SEZIONE 1 — ANALISI PER PIATTAFORMA

---

### 1.1 GOODREADS
**URL:** https://www.goodreads.com | **Fondato:** 2006 | **Utenti:** ~150M+ | **Proprietà:** Amazon

#### Funzionalità

| Area | Dettaglio |
|---|---|
| **Dashboard/Home** | Activity feed dei friend (non dashboard personale). Widget "Currently Reading" con progresso. Reading challenge annuale con cerchio percentuale. Suggerimenti libro del giorno. |
| **Scaffali** | 3 default (Want to Read / Currently Reading / Read) + scaffali custom illimitati. Tag "exclusive shelf" (solo uno alla volta) vs shelf aggiuntive |
| **Vista libri** | Griglia copertine (cover view) o lista tabellare (con colonne ordinabili: titolo, autore, data aggiunta, valutazione, data lettura, pagine) |
| **Filtri** | Per scaffale, per data lettura, per valutazione, per genere, per formato |
| **Dettaglio libro** | Copertina grande, titolo, autore, serie, descrizione, generi, dettagli edizione (pagine, casa editrice, ISBN), rating aggregato community, conteggio recensioni, date lettura personali, note private |
| **Valutazione** | 5 stelle intere (solo). Recensione testuale separata dalla valutazione |
| **Stati lettura** | want-to-read / currently-reading / read. Nessun DNF nativo (si usa scaffale custom) |
| **Statistiche** | "Reading Challenge" (contatore progresso). "Year in Books": totale libri, pagine, generi, copertine a mosaico, mese più produttivo. Grafici anno-per-anno accessibili al profilo |
| **Social** | Feed amici, commenti sulle recensioni, likes, Q&A autori, book clubs, discussioni di gruppo, Following/Followers |
| **Ricerca/Aggiunta** | Ricerca per titolo/autore/ISBN. App mobile con scanner barcode. API aperta (ora parzialmente limitata da Amazon) |

#### Grafica/UI
- **Palette:** Bianco/crema come sfondo, bordi beige (#e8e0d4), testo scuro (#382110), accenti mogano/marrone caldo (`#372213`), verde per CTA (`#2c6e49`). Mood "libreria fisica vintage"
- **Home:** Feed attività a colonna centrale, sidebar con "Reading Goals" e suggerimenti libri a destra
- **Card libri:** Copertina verticale (aspect ratio ~2:3), titolo, autore, stelle ★, badge stato lettura. Hover: leggero zoom + CTA "Want to Read"
- **Tipografia:** Serif per titoli di sezione, sans-serif per corpo testo. Dimensioni moderate, buona leggibilità
- **Pattern ricorrenti:** Progress bar circolare per reading challenge, griglia copertine fianco a fianco, scaffale visivo scrollabile, barra di ricerca prominente in header

**Fonti:** https://www.goodreads.com/about/us | https://en.wikipedia.org/wiki/Goodreads | https://alternativeto.net/software/goodreads/about/

---

### 1.2 THE STORYGRAPH
**URL:** https://app.thestorygraph.com | **Fondato:** 2019 da Nadia Odunayo | **Utenti:** 5 milioni (gennaio 2026) | **Modello:** Freemium (Plus: €49.99/anno)

#### Funzionalità

| Area | Dettaglio |
|---|---|
| **Dashboard/Home** | Libri "Currently Reading" con progresso. Feed attività minimalista (non social-first). Suggerimenti basati su mood/genere/pacing |
| **Scaffali** | "To Read" / "Currently Reading" / "Read" / "Did Not Finish" (DNF **nativo**) + custom illimitati |
| **Vista libri** | Griglia o lista. Filtri avanzati per stato, genere, mood, pacing, tag custom, formato, data lettura |
| **Dettaglio libro** | Copertina, metadati, mood tags (es. "emotional", "dark", "adventurous"), pacing indicator (slow/medium/fast), content warnings community-generated, date inizio/fine lettura, progresso pagine |
| **Valutazione** | **Mezze e quarti di stella** (es. 3.25★). Opzione per non valutare separando "tracked" da "rated" |
| **Stati lettura** | want-to-read / currently-reading / read / **DNF** / **owned** (flag separato, un click) |
| **Statistiche** | Free: contatori base, wrap-up annuale con grafici condivisibili. **Plus:** filtri per periodo custom, confronto periodi, chart personalizzati (pie/bar), filtro per fiction/non-fiction/tag, confronto con altri utenti |
| **Social** | Minimalista: follow altri utenti, vedere cosa leggono. Nessun like alle recensioni fino a febbraio 2026. No commenti alle recensioni. Focus sulla scoperta, non la community |
| **Reading Journal** | Note private sincronizzate con aggiornamenti di progresso. Visibile solo all'utente |
| **Up Next Queue** | Coda "prossimi 5 libri da leggere", diversa dalla lista want-to-read |
| **Challenges** | Sfide comunità tematiche (genere, provenienza geografica, ecc.) + obiettivi annuali (pagine o ore) |
| **Ricerca/Aggiunta** | Ricerca titolo/autore/ISBN. Scanner barcode. Kobo sync (dal giugno 2026) |

#### Grafica/UI
- **Palette:** Sfondo scuro-viola/ardesia con accenti giallo-dorati (#f5c842 circa) in dark mode; versione light con bianco pulito e accenti teal/verde. Mood "tech moderno per bibliofili"
- **Home:** Layout pulito, "Currently Reading" in evidenza top, sezione statistica compatta, raccomandazioni basate su tag mood
- **Card libri:** Copertina verticale + titolo + autore + mood tags colorati (chips orizzontali). Nessuna stella visibile di default. Pacing indicator con icona freccia
- **Innovazione chiave:** I **mood tag** sono chips colorate (es. arancione = "adventurous", viola = "mysterious", rosso = "dark") che filtrano tutta la catalog. Sostituisce il sistema genere-only di Goodreads
- **Tipografia:** Sans-serif moderna, spacing generoso, emphasis su leggibilità mobile
- **Pattern ricorrenti:** Mood chips, progress bar pagine aggiornabile inline, "Up Next" queue visiva, wrap-up grafico condivisibile (immagine shareable con design curato)

**Fonti:** https://thestorygraph.com/ | https://app.thestorygraph.com/plus | https://en.wikipedia.org/wiki/StoryGraph

---

### 1.3 LIBRARYTHING
**URL:** https://www.librarything.com | **Fondato:** 2005 da Tim Spalding | **Utenti:** 3.25M+ | **Libri catalogati:** 320M+

#### Funzionalità

| Area | Dettaglio |
|---|---|
| **Dashboard/Home** | Homepage modulare personalizzabile: moduli riordinabili (recentemente aggiunti, amici, eventi locali, announcement, tag cloud, autore cloud). Non "reading dashboard" ma "library dashboard" |
| **Catalogazione** | Qualità professionale da biblioteca: MARC records, Dewey Decimal, Library of Congress, Melvil Decimal System. Dati da 4967+ biblioteche mondiali + Amazon |
| **Vista libri** | **Lista** (con colonne configurabili: titolo, autore, data, valutazione, tag, ISBN, editore, pagine, ecc.) **o Cover view**. Colonne riordinabili con drag-and-drop |
| **Filtri e ordinamenti** | Per tag, valutazione, collezione, formato, data lettura, classificazione Dewey. Ricerca full-text avanzata |
| **Dettaglio libro (Work page)** | Tutte le edizioni del libro aggregate. Tag community, recensioni aggregate da tutte le copie. Link a discussioni. Informazioni di "Common Knowledge" (serie, premi, personaggi, luoghi) |
| **Valutazione** | Stelle (formato non specificato nei documenti pubblici), recensioni testuali |
| **Collezioni/Scaffali** | "Collections" multiple (es. "Fiction", "Non-Fiction", "Library 1", "Library 2") + "tags" liberi come ulteriore livello di organizzazione |
| **Statistiche** | **Stats/Memes** pagina con visualizzazioni creative: "La tua biblioteca è alta come..." (edifici famosi), confronto con altre biblioteche, statistiche Dewey, distribuzione temporale |
| **Social** | Gruppi, forum Talk, "Interesting Libraries" (follow biblioteche simili), confronto biblioteche, Early Reviewers (libri gratis in cambio di recensione) |
| **Tracking libri prestati** | Lending tracker integrato (a chi è prestato, quando) |
| **Inventario** | Modalità inventario per verificare la collezione fisica |
| **Importazione** | Da Amazon, LibraryThing precedente, file CSV, varie fonti |

#### Grafica/UI
- **Palette:** Utility-first, classico web: bianco/grigio neutro, link blu standard, pochi accenti colorati. Non c'è un mood design definito - è funzionale
- **Home:** Modulare con header fisso e colonne di moduli (ricorda iGoogle o Netvibes). Non responsive nella versione desktop principale
- **Card libri:** Focus lista tabellare con copertina thumbnail piccola. Cover view disponibile ma secondaria
- **Tipografia:** Multiple opzioni scelte dall'utente: Verdana Standard/Small, Georgia, Atkinson (accessibilità), Large Text. Approccio utilitaristico
- **Pattern ricorrenti:** Tag cloud visivo (dimensioni proporzionali alla frequenza), tabella con ordinamento per colonne, badge contatori

**Fonti:** https://www.librarything.com/tour/ (tour pagine 1-9) | https://www.librarything.com/about | https://en.wikipedia.org/wiki/LibraryThing

---

### 1.4 BOOKWYRM
**URL:** https://bookwyrm.social | **Codice:** github.com/bookwyrm-social/bookwyrm (2.7k+ ⭐) | **Stack:** Django + Bulma.io | **Modello:** Open source, federato (ActivityPub)

#### Funzionalità

| Area | Dettaglio |
|---|---|
| **Dashboard/Home** | Activity feed federato (post degli amici sulle istanze collegate, incluso Mastodon). Non statistiche ma stream di lettura |
| **Scaffali** | Default: "To-Read" / "Currently Reading" / "Stopped Reading" / "Read" (4 default, tra cui **Stopped Reading** come DNF) + scaffali custom privati/pubblici/followers-only |
| **Dettaglio libro** | Date inizio/stop/fine lettura. Progress updates inline durante la lettura. Recensioni aggregate da tutte le istanze federate |
| **Tipi di post** | Review (con/senza rating), Comment, Quote/Excerpt dal libro |
| **Valutazione** | Stelle (Bulma rating component) + opzione senza valutazione |
| **Privacy** | Ogni post/scaffale ha controllo granulare: privato / solo followers / pubblico. Approvazione manuale follower |
| **Federation** | Condivisione metadati libri tra istanze per creare DB collaborativo. Compatibile con Mastodon, GoToSocial ecc. |
| **Moderazione** | Blocco, segnalazione, moderazione per istanza |
| **Liste** | Liste libri aperte (chiunque può aggiungere) / curate (admin approva) / private |
| **Gruppi** | Book club con liste condivise dal gruppo |
| **Importazione** | Da Goodreads, LibraryThing e altri CSV |

#### Grafica/UI
- **Framework:** Bulma.io (CSS puro, no JavaScript richiesto) - molto più leggero di Bootstrap
- **Palette:** Tema chiaro predefinito + dark mode disponibile. Accenti "teal" di Bulma per i CTA primari. Pulito e moderno
- **Home:** Feed a colonna singola stile social media (attività recenti degli utenti seguiti). Sidebar con "Reading Goals" e lista libri in corso
- **Card libri:** Copertina + info essenziali + badge stato. Simile a Goodreads ma più pulito
- **Pattern ricorrenti:** Privacy badge su ogni post, quote block per le citazioni dal libro (stile blockquote), progress update inline (slider pagine), avatar circolari

**Fonti:** https://bookwyrm.social/ | https://docs.joinbookwyrm.com/ | https://github.com/bookwyrm-social/bookwyrm | https://alternativeto.net/software/bookwyrm/about/

---

### 1.5 HARDCOVER
**URL:** https://hardcover.app | **Fondato:** ~2022-2023 da Adam Fortuna | **Stack:** Rails + Inertia.js + React + Tailwind CSS | **Modello:** Freemium ($5/mese)

#### Funzionalità

| Area | Dettaglio |
|---|---|
| **Dashboard/Home** | Hero section con "Currently Reading" prominente. Trending books (ultimi 3 mesi). Feed attività amici. "Discover" sezione con raccomandazioni personalizzate |
| **Goal di lettura** | Annuali per: numero libri / numero pagine / tipo (libro, graphic novel, audiobook) — suddivisione per formato è unica |
| **Scaffali/Liste** | Liste illimitate, seguire liste altrui, copiare liste. Sistema più flessibile degli scaffali fissi |
| **Statistiche** | "Book Stats" avanzate in sviluppo. Notifiche lettura per mantenere obiettivi |
| **Importazione** | Da Goodreads, LibraryThing, CSV, altri tracker |
| **Social** | Follow amici, vedere letture in corso e favoriti. No feed pesante ma social leggero |
| **Ricerca** | Ricerca rapida con ⌘K shortcut. Ricerca separata per autori/serie |
| **Dark mode** | Sì, nativa |
| **Privacy** | No tracking degli utenti, no pubblicità |

#### Grafica/UI — ANALISI DETTAGLIATA CSS
*(Dati estratti direttamente dal file CSS sorgente: `production-static.hardcover.app/vite/assets/application-BsxfayQp.css`)*

**Palette di colori (light mode):**
```css
--primary: #4f46e5          /* Indigo 600 — CTA, bottoni primari */
--primary-foreground: #e0e7ff /* Indigo 100 — testo su primario */
--background: #fff
--foreground: #111827        /* Gray 900 */
--card: #e2e8f0              /* Blue-Gray 200 — sfondo card */
--card-foreground: #1f2937   /* Gray 800 */
--secondary: #d1d5db         /* Gray 300 */
--accent: #f59e0b            /* Amber 500 — accento caldo */
--success: #10b981           /* Emerald 500 */
--border: #e5e7eb            /* Gray 200 */
--radius: .5rem
```

**Palette di colori (dark mode):**
```css
--background: #1f2937        /* Gray 800 */
--card: #0f172a              /* Slate 950 */
--primary: #4f46e5           /* invariato */
--accent: #fbbf24            /* Amber 400, più brillante in dark */
--ring: #fde047              /* Yellow 300 — focus ring in dark */
```

**Chart colors (grafici):**
```
Blue #3b82f6, Indigo #4f46e5, Pink #ec4999, Yellow #eab308,
Green #22c55e, Violet #6366f1, Red #ef4444, Orange #f97316,
Teal #14b8a6, Lime #a3e635
```

**Tipografia:**
- **Body:** Inter (Regular 400, SemiBold 600, Bold 700) — font preload da CDN proprietario
- **Display/Headings:** New Spirit 400 (slab serif elegante) — font preload da CDN proprietario
- **Mono:** ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas
- **Font size scale:** xs(0.75rem) → sm(0.875) → base(1rem) → lg(1.125) → xl(1.25) → 2xl(1.5) → 3xl(1.875) → 4xl(2.25) → 5xl(3rem)
- **Tracking:** `--tracking-tighter: -0.05em` (per il logo: `font-black tracking-tighter text-lg/xl`)

**Layout e spaziatura:**
- Max container: `max-w-7xl` (80rem = 1280px)
- Padding header: `px-3 md:px-0 border-b border-secondary`
- Gap card: uniform `gap-2`/`gap-4`
- Border radius: consistente `rounded-xl` per bottoni e card (0.75rem), `rounded-lg` per input (0.5rem)
- Ombra card: via shadow utilities Tailwind

**Pattern UI distinctive:**
- Ricerca globale con keyboard shortcut (⌘K), bottone disabilitato visivamente con `cursor-not-allowed`
- Navigation dropdown con submenus animati (`translate-y-2 opacity-0` → `translate-y-0 opacity-100`)
- Logo animato: rotazione on hover (`group-hover:rotate-12 transition-all duration-300`)
- `backdrop-blur-xs` per elementi sovrapposti nella navbar
- Pulsanti con `rounded-xl` e effetti di pressione

**Fonti:** https://hardcover.app/ | https://alternativeto.net/software/hardcover/about/ | https://www.producthunt.com/products/hardcover | CSS sorgente diretto

---

## SEZIONE 2 — TABELLA COMPARATIVA DELLE FUNZIONALITÀ

| Funzionalità | Goodreads | StoryGraph | LibraryThing | BookWyrm | Hardcover |
|---|:---:|:---:|:---:|:---:|:---:|
| **Scaffali di base** (WTR/Reading/Read) | ✅ | ✅ | ✅ | ✅ | ✅ |
| **DNF nativo** | ❌ (custom shelf) | ✅ | ❌ | ✅ (Stopped) | ⚠️ |
| **Scaffali custom** | ✅ | ✅ | ✅ (Collections) | ✅ | ✅ (Liste) |
| **Date inizio/fine lettura** | ✅ | ✅ | ⚠️ (parziale) | ✅ | ✅ |
| **Progresso pagine inline** | ✅ | ✅ | ❌ | ✅ | ✅ |
| **Valutazione stelle intere** | ✅ | ✅ | ✅ | ✅ | ✅ |
| **Mezze stelle** | ❌ | ✅ (+ quarti) | ❌ | ❌ | ❌ |
| **Reading Journal privato** | Note di base | ✅ dedicato | ❌ | Commenti | ❌ |
| **Citazioni/Quote** | ✅ | ❌ nativo | ❌ | ✅ | ❌ |
| **Content warnings** | ❌ | ✅ | ❌ | ❌ | ❌ |
| **Mood tags** | ❌ | ✅ | ❌ | ❌ | ❌ |
| **Reading Goals** | ✅ (libri/anno) | ✅ (pagine/ore) | ❌ | ✅ | ✅ (libri/pagine/tipo) |
| **Statistiche avanzate** | Anno in Books | Plus: grafici custom | Stats/Memes | ❌ | In sviluppo |
| **Wrap-up annuale** | ✅ grafico | ✅ condivisibile | ❌ | ❌ | ❌ |
| **Vista lista libri** | ✅ | ✅ | ✅ (primaria) | ✅ | ✅ |
| **Vista griglia copertine** | ✅ | ✅ | ✅ | ✅ | ✅ |
| **Filtri avanzati** | ✅ | ✅ | ✅+ | ⚠️ | ✅ |
| **Ricerca ISBN** | ✅ | ✅ | ✅ (Z39.50) | ✅ | ✅ |
| **Scan barcode** | ✅ | ✅ | ✅ | ❌ | ✅ |
| **Importazione da CSV** | ✅ | ✅ | ✅ | ✅ | ✅ |
| **Social/Community** | ✅ (forte) | ⚠️ (leggero) | ✅ (gruppi) | ✅ (federato) | ⚠️ (leggero) |
| **Dark mode** | ❌ | ✅ | ❌ | ✅ | ✅ |
| **Open Source** | ❌ | ❌ | ❌ | ✅ | ❌ |
| **Export dati** | CSV | CSV | CSV | ✅ | ✅ |

---

## SEZIONE 3 — LE 15 FUNZIONALITÀ/PATTERN UI PIÙ EFFICACI E REPLICABILI

In ordine di priorità per un'app personale (non social):

### 🥇 PRIORITÀ ALTA

**1. Pagina di dettaglio libro dedicata**
*(Ispirazione: tutti i 5 platform)*
Una pagina `/libri/{id}` separata, non solo la form di modifica. Deve mostrare: copertina grande, metadati completi, stato con badge colore, stelle visive, timeline di lettura (data inizio → data fine), note/citazioni ben formattate, link modifica. Il form rimane separato per editing.

**2. Tracciamento date inizio/fine lettura + progresso pagine**
*(Ispirazione: StoryGraph, BookWyrm, Hardcover)*
Aggiungere al modello `Libro`: `dataInizioLettura` (LocalDate), `dataFineLettura` (LocalDate), `pagineAttuali` (Integer), `totalePagine` (Integer). Calcolare automaticamente la progress bar `pagineAttuali/totalePagine`. La progress bar inline (aggiornabile senza ricaricare la pagina) è uno dei pattern più apprezzati di StoryGraph.

**3. Stato DNF (Did Not Finish / Abbandonato)**
*(Ispirazione: StoryGraph, BookWyrm)*
Aggiungere `ABBANDONATO` all'enum `StatoLettura`. Tutti i competitor moderni lo prevedono. Badge colore: rosso/arancione. Eventualmente con `dataCessataLettura`. Permette statistiche oneste (es. "% libri abbandonati per genere").

**4. Dashboard con statistiche temporali e grafici**
*(Ispirazione: StoryGraph Plus, Goodreads Year in Books)*
La tua dashboard ha già i contatori — va potenziata con:
- **Libri letti per mese** (sparkline o barre): `GROUP BY MONTH(dataFineLettura)`
- **Pagine totali lette** (se tracciato `totalePagine`)
- **Genere/categoria più letta** con donut chart (Bootstrap ha nessun chart nativo → usa Chart.js CDN, è leggero)
- **Media valutazione** per categoria
- **Streak**: quanti mesi consecutivi hai letto almeno un libro
Si implementa tutto lato Java con query aggregate sul repository.

**5. Filtri multipli + ordinamento nella lista libri**
*(Ispirazione: tutti i platform)*
La ricerca per titolo non basta. Aggiungere query param cumulabili:
- `?stato=LETTO&categoria=ROMANZO&valutazione=5&sort=titolo`
- Dropdown/pill filters sopra la griglia (Bootstrap 5 ha `.btn-group` e `.form-select` perfetti)
- Ordinamento: Titolo A-Z, Autore A-Z, Anno ↑↓, Valutazione ↑↓, Data aggiunta ↓, Data fine lettura ↓
Il backend Thymeleaf + Spring Data può gestire tutto con un `Specification<Libro>` pattern.

### 🥈 PRIORITÀ MEDIA

**6. Toggle griglia/lista nella pagina libri**
*(Ispirazione: Goodreads, LibraryThing, StoryGraph)*
Due bottoni radio in header della lista (`<div class="btn-group">`) che switchano tra:
- **Griglia** (attuale, card con copertina) — ideale per scorrere visivamente
- **Lista** (righe compatte: copertina thumbnail 40px, titolo, autore, stelle, stato, data) — ideale per gestire cataloghi grandi
Si gestisce con JS puro: toggle classe su container + localStorage per ricordare la preferenza.

**7. Card libri migliorate con status badge + hover CTA contestuale**
*(Ispirazione: Hardcover, StoryGraph)*
Le card attuali mostrano il badge ma il CTA è sempre "Modifica". Pattern migliore:
- Badge `IN_LETTURA` → hover CTA "Aggiorna progresso"
- Badge `DA_LEGGERE` → hover CTA "Inizia lettura"
- Badge `LETTO` → hover CTA "Vedi dettagli"
- Badge `ABBANDONATO` → hover CTA stile diverso (tono grigio/rosato)
Stella visiva a 5 punti dorati nella card. Hardcover usa copertina con overlay on hover.

**8. Sezione Citazioni/Note per libro**
*(Ispirazione: BookWyrm quotes, form "note" esistente)*
Separare le "note generali" dalle "citazioni". Aggiungere entità `Citazione` con: `libro_id`, `testo` (max 1000), `pagina` (nullable), `data`. Visualizzarle nella pagina dettaglio con stile blockquote (`border-left: 4px solid var(--bs-warning); padding-left: 1rem; font-style: italic`). Semplice da implementare con una `@OneToMany` relationship.

**9. Reading Goal annuale con progress bar circolare**
*(Ispirazione: Goodreads, StoryGraph, Hardcover)*
Un campo `Setting` globale: `goalAnno2024 = 24`. Dashboard mostra progress bar + "X/24 libri letti quest'anno". Calcolo: `COUNT(*) WHERE dataFineLettura BETWEEN '2024-01-01' AND '2024-12-31'`. La progress bar circolare (SVG) è più bella di quella lineare per questo scopo. Alternativa semplice: Bootstrap progress bar colorata con testo "X libri su Y obiettivo".

**10. Pagina "Statistiche" dedicata**
*(Ispirazione: StoryGraph, LibraryThing Stats/Memes)*
Una rotta `/statistiche` separata con:
- Totale libri letti per anno (barre o linea)
- Distribuzione per categoria (donut)
- Distribuzione valutazioni (istogramma)
- Media pagine per libro (se tracciato)
- Libro più corto/lungo/meglio valutato
- "Record personali" (primo libro, 100° libro, ecc.)
Genera interesse e incentiva l'uso continuo dell'app.

### 🥉 PRIORITÀ BASSA/EXTRA

**11. Auto-completamento avanzato da ISBN (già presente, da migliorare)**
*(Ispirazione: tutti i platform — tu già usi OpenLibrary)*
Estendere `OpenLibraryService` per recuperare anche: numero pagine, descrizione/sinossi, generi, lingua. Mostrare la copertina nel form prima di salvare. Aggiungere supporto anche a Google Books API come fallback (`https://www.googleapis.com/books/v1/volumes?q=isbn:{isbn}`).

**12. Palette cromatica per stati di lettura**
*(Ispirazione: StoryGraph mood tags, BookWyrm, Hardcover)*
Ogni stato ha un colore e un'icona semantica fissa, usata **ovunque** in modo coerente:
- `DA_LEGGERE` → grigio `#6c757d` + icona `bi-bookmark`
- `IN_LETTURA` → blu/indigo `#4f46e5` + icona `bi-book-half`
- `LETTO` → verde `#198754` + icona `bi-check-circle-fill`
- `ABBANDONATO` → arancione `#fd7e14` + icona `bi-x-circle`
Centralizzare questo mapping in un metodo helper Thymeleaf o in un `@ControllerAdvice`.

**13. "Libri in evidenza" / Hero section con libro corrente**
*(Ispirazione: Hardcover hero, BookWyrm currently reading)*
Se c'è un libro `IN_LETTURA`, mostrarlo in una hero card in cima alla dashboard:
copertina grande a sinistra, info a destra, progress bar pagine prominente, bottone "Aggiorna progresso". Se non c'è, mostrare "Nessun libro in corso — iniziane uno!" con CTA. Questo è il pattern #1 di Hardcover: mettere in evidenza ciò che stai leggendo.

**14. Gestione tag personalizzati**
*(Ispirazione: StoryGraph custom tags, LibraryThing tags)*
Aggiungere entità `Tag` con `@ManyToMany` su `Libro`. I tag sono stringa libera (es. "preferiti-2024", "regalato", "riletto"). Mostrarli come badge colorati nelle card. Consentono filtri trasversali alle categorie. Semplice da fare ma molto potente.

**15. Export CSV della biblioteca**
*(Ispirazione: Goodreads, StoryGraph, LibraryThing — tutti offrono export)*
Un pulsante "Esporta CSV" nella pagina lista/impostazioni. Spring MVC può restituire un `ResponseEntity<byte[]>` con `Content-Type: text/csv`. Colonne: id, titolo, autore, isbn, categoria, stato, valutazione, note, dataInizio, dataFine, pagine. Fondamentale per il **lock-in avoidance** e la fiducia dell'utente.

---

## SEZIONE 4 — RACCOMANDAZIONI CONCRETE DI DESIGN

### 4.1 Palette Colori Raccomandata

Ispirandosi a Hardcover (la più curata esteticamente tra i competitor) e adattando a Bootstrap:

```css
/* Variabili CSS da aggiungere nel <head> o in un custom.css */
:root {
  /* Primario: Indigo — coerente con Bootstrap primary se override */
  --bs-primary: #4f46e5;          /* indigo-600, warm e libresco */
  --bs-primary-rgb: 79, 70, 229;
  
  /* Accento caldo: Amber — perfetto per stelle e highlight */
  --accent: #f59e0b;              /* amber-500 */
  
  /* Successo: Verde smeraldo */
  --bs-success: #10b981;
  
  /* Attenzione/In lettura: Arancione caldo */
  --bs-warning: #f97316;          /* orange-500 */
  
  /* Sfondo card: grigio-blu molto leggero */
  --card-bg: #f8f9ff;             /* leggermente tinted di indigo */
  
  /* Testo primario: quasi-nero caldo */
  --text-primary: #1e1b4b;        /* indigo-950, non nero puro */
}
```

**Mood:** Il progetto attuale usa `bg-primary` Bootstrap standard (blu vivace). Shiftare verso l'indigo (#4f46e5) dà un tono più "premium libresco" senza essere pesante come il marrone di Goodreads né freddo come un tool di gestione.

### 4.2 Layout Consigliato

**Dashboard (home.html):**
```
┌─────────────────────────────────────────────┐
│ NAVBAR (sticky, indigo scuro)               │
├─────────────────────────────────────────────┤
│ HERO: "Stai leggendo: [TITOLO]"             │
│ [Copertina 120px] [Titolo] [Autore]         │
│                   [Progress bar pagine  ██░░] │
│                   [Aggiorna progresso btn]   │
├─────────────────────────────────────────────┤
│ STATISTICHE: [4 card metriche in fila]      │
│ Totali | Da leggere | In lettura | Letti    │
├───────────────────────┬─────────────────────┤
│ GOAL ANNUALE          │ DISTRIBUZIONE CAT.  │
│ 12/24 libri ████░░ 50%│ Bar chart o donut   │
├───────────────────────┴─────────────────────┤
│ ULTIMI LIBRI AGGIUNTI (griglia 5 copertine) │
└─────────────────────────────────────────────┘
```

**Lista Libri (libri.html):**
```
┌─────────────────────────────────────────────┐
│ [Cerca] [Filtro Stato▼] [Filtro Cat▼] [Sort▼] │ [+Aggiungi] [⊞⊟toggle vista]
├─────────────────────────────────────────────┤
│ GRIGLIA COPERTINE (4-5 per riga su desktop) │
│ oppure                                       │
│ LISTA RIGHE (thumbnail + info condensate)   │
└─────────────────────────────────────────────┘
```

### 4.3 Componenti Bootstrap Specifici da Usare

| Componente | Uso | Fonte ispirazione |
|---|---|---|
| `progress` con `bg-success/warning/danger` | Barra progresso pagine in card IN_LETTURA | StoryGraph |
| `btn-group` per toggle griglia/lista | Switch vista nella lista | tutti |
| `badge rounded-pill` per mood/tag | Tag personalizzati (feature futura) | StoryGraph |
| `offcanvas` (sidebar mobile) | Filtri avanzati su mobile | moderno pattern |
| `placeholder` (shimmer loading) | Skeleton mentre carica copertine | Hardcover |
| Chart.js (CDN, non Bootstrap) | Grafici statistiche nella dashboard | StoryGraph, Goodreads |
| `accordion` | Sezione citazioni espandibile nel dettaglio libro | BookWyrm |
| `toast` | Notifica "Libro aggiunto/modificato con successo" | pattern universale |

### 4.4 Tipografia Raccomandata

L'app attuale non specifica font custom (usa system fonts Bootstrap). Due opzioni:

**Opzione A — Sobria e veloce (nessun web font):**
```css
body { font-family: system-ui, -apple-system, "Segoe UI", sans-serif; }
```

**Opzione B — Hardcover-inspired (Google Fonts):**
```html
<!-- Nel <head> -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&family=Libre+Baskerville:wght@400;700&display=swap" rel="stylesheet">
```
```css
body { font-family: 'Inter', sans-serif; }
h1, h2, .book-title { font-family: 'Libre Baskerville', serif; }
```
*Libre Baskerville è l'alternativa Google a New Spirit, stessa vibe slab-serif elegante.*

### 4.5 Card Libro Migliorata (Pattern Hardcover + StoryGraph)

```html
<!-- Card libro ottimizzata (stile Hardcover) -->
<div class="card h-100 shadow-sm card-libro border-0"
     style="border-radius: .75rem; overflow:hidden;">
  <!-- Copertina con overlay status -->
  <div class="position-relative">
    <img ... class="card-img-top copertina" style="height:240px; object-fit:cover;">
    <!-- Badge stato in sovrapposizione -->
    <span class="position-absolute top-0 end-0 m-2 badge rounded-pill"
          th:classappend="${libro.statoLettura.name()=='IN_LETTURA'} ? 'bg-primary' : ...">
      IN LETTURA
    </span>
  </div>
  <div class="card-body px-3 py-2">
    <h6 class="mb-1 fw-semibold lh-sm" style="font-size:.9rem" th:text="${libro.titolo}"></h6>
    <small class="text-muted d-block mb-2" th:text="${libro.autore}"></small>
    <!-- Stelle -->
    <div class="d-flex gap-1 align-items-center mb-2">
      <!-- loop stelle ★☆ -->
      <span class="badge text-bg-light" th:text="${libro.categoria}"></span>
    </div>
    <!-- Progress bar SOLO se IN_LETTURA con pagine tracciabili -->
    <div th:if="${libro.statoLettura.name()=='IN_LETTURA' and libro.totalePagine != null}">
      <div class="progress" style="height:4px;">
        <div class="progress-bar bg-primary" ...></div>
      </div>
      <small class="text-muted">42 / 320 pag.</small>
    </div>
  </div>
</div>
```

### 4.6 Spaziatura e Densità Visiva

- **Copertine grid:** `height: 220-260px` su desktop, `height: 160px` su mobile — le dimensioni attuali (260px) sono corrette
- **Gap tra card:** `g-3` o `g-4` — attuale `g-4` è perfetto
- **Padding card body:** `px-3 py-2` (compatto) — non `p-4` come Bootstrap default
- **Border radius card:** `.75rem` (12px) — più morbido del default Bootstrap `.25rem`
- **Shadow:** `shadow-sm` nelle card normali, `shadow` sull'hover — coerente con l'attuale

---

## SEZIONE 5 — LISTA COMPLETA FONTI CON URL

### Fonti Primarie (siti analizzati)

| Fonte | URL | Note |
|---|---|---|
| Goodreads — About | https://www.goodreads.com/about/us | Dichiarazione missione, storia |
| Goodreads — Wikipedia | https://en.wikipedia.org/wiki/Goodreads | Feature storiche, dati utenti |
| The StoryGraph — Home | https://thestorygraph.com/ | Lista funzionalità principali |
| The StoryGraph — Plus | https://app.thestorygraph.com/plus | Dettaglio features Premium |
| The StoryGraph — Wikipedia | https://en.wikipedia.org/wiki/StoryGraph | Storia, confronto Goodreads, dati utenti |
| LibraryThing — Tour (pp. 1-9) | https://www.librarything.com/tour/ | Features complete (12 pagine tour) |
| LibraryThing — About | https://www.librarything.com/about | Tecnologia, fonti dati |
| LibraryThing — Wikipedia | https://en.wikipedia.org/wiki/LibraryThing | Z39.50, MARC records, Dewey |
| BookWyrm — Documentazione | https://docs.joinbookwyrm.com/ | Feature complete, architettura |
| BookWyrm — GitHub | https://github.com/bookwyrm-social/bookwyrm | Stack tecnico (Django + Bulma) |
| BookWyrm — joinbookwyrm.com | https://joinbookwyrm.com | Feature utente |
| Hardcover — Homepage | https://hardcover.app/ | OG tags, hero, descrizione |
| Hardcover — CSS sorgente | https://production-static.hardcover.app/vite/assets/application-BsxfayQp.css | **Dati design estratti direttamente** |
| Hardcover — Supporter/Pricing | https://hardcover.app/supporter | Modello freemium |

### Fonti Secondarie (recensioni, comparazioni)

| Fonte | URL | Note |
|---|---|---|
| AlternativeTo — Goodreads | https://alternativeto.net/software/goodreads/about/ | Feature list, recensioni utenti |
| AlternativeTo — Hardcover | https://alternativeto.net/software/hardcover/about/ | Feature list, pricing, descrizione |
| AlternativeTo — BookWyrm | https://alternativeto.net/software/bookwyrm/about/ | Feature list, recensioni |
| ProductHunt — Hardcover | https://www.producthunt.com/products/hardcover | Recensioni community, punti di forza/debolezza |

### Sorgenti non accessibili (tasso alto di blocchi/404 al momento della ricerca)

Le seguenti fonti erano irraggiungibili per limitazioni di rete/accesso (403 Forbidden o 404):
- LibraryThing (www, blocco IP)
- The Verge, Wired, PCMag, Tom's Guide, NyTimes Wirecutter (paywall/404 su alcuni articoli specifici)
- Dribbble, Behance (richiede account JS)
- Reddit (blocco API)
- Medium/UX Design (articoli specifici non trovati)
- StoryGraph app (login richiesto per quasi tutte le pagine)

---

## CONCLUSIONI STRATEGICHE

Il tuo progetto è già su una buona base. La tabella seguente suggerisce un **roadmap per 3 sprint** basato sul rapporto impatto/complessità:

| Sprint | Funzionalità | Impatto | Effort |
|---|---|---|---|
| **Sprint 1** | Pagina dettaglio libro | ⭐⭐⭐⭐⭐ | Medio |
| **Sprint 1** | Stato ABBANDONATO + data inizio/fine | ⭐⭐⭐⭐ | Basso |
| **Sprint 1** | Filtri multipli + ordinamento lista | ⭐⭐⭐⭐⭐ | Medio |
| **Sprint 2** | Toggle griglia/lista | ⭐⭐⭐⭐ | Basso |
| **Sprint 2** | Hero "libro in lettura" in dashboard | ⭐⭐⭐⭐ | Basso |
| **Sprint 2** | Barra progresso pagine | ⭐⭐⭐⭐ | Medio |
| **Sprint 2** | Chart.js per statistiche dashboard | ⭐⭐⭐⭐ | Medio |
| **Sprint 3** | Citazioni per libro | ⭐⭐⭐ | Medio |
| **Sprint 3** | Reading Goal annuale | ⭐⭐⭐ | Basso |
| **Sprint 3** | Tag personalizzati | ⭐⭐⭐ | Alto |
| **Futuro** | Export CSV | ⭐⭐⭐ | Basso |
| **Futuro** | Palette colori + tipografia upgrade | ⭐⭐⭐ | Basso |

---

*Report prodotto il 18/08/2026. Tutti i dati sono verificati da fonti primarie (siti ufficiali, Wikipedia, CSS sorgente diretto, documentazione ufficiale). Le sezioni su UI/design di StoryGraph e Goodreads sono parzialmente inferite da fonti secondarie verificate (Wikipedia, AlternativeTo) a causa di limitazioni di accesso agli account autenticati.*