---
name: chiusura-sessione
description: 'Procedura di chiusura sessione o sprint: aggiorna documentazione, committa e pusha. Usare quando l''utente dice "chiudiamo", "fine sessione", "fine sprint", "committa e pusha", o prima di terminare il lavoro.'
---

# Chiusura sessione / sprint

Regola del progetto (decisa dall'utente il 18/08/2026): **a fine sessione e a fine sprint si aggiorna la documentazione, si committa e si pusha sempre**. Non lasciare mai lavoro solo in locale.

## Checklist da eseguire in ordine

### 1. Verifica lo stato

```bash
git status --short
git log origin/main..HEAD --oneline
```

### 2. Aggiorna la documentazione se serve

- **README.md**: se sono state aggiunte funzionalità visibili all'utente (nuove pagine, campi, endpoint), aggiorna la sezione "Funzionalità implementate" e la struttura del progetto.
- **report-app-biblioteche.md**: se uno sprint è completato, segnalo nella roadmap (es. spunta le voci fatte).
- **.github/copilot-instructions.md**: solo se sono cambiate convenzioni, comandi o architettura.

### 3. Compila e verifica che non ci siano rotture

```bash
cd biblioteca-personale && ./mvnw compile
```

Se l'app è in esecuzione con devtools, la ricompilazione triggera un restart: verifica che gli endpoint principali rispondano ancora (`curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/` deve dare 200).

### 4. Committa

- Messaggi di commit **in italiano**, stile descrittivo (es. "Aggiunta pagina dettaglio libro con date di lettura").
- Un commit logico per argomento; se ci sono più cambiamenti indipendenti, fai commit separati.
- Includi sempre il trailer: `Co-authored-by: Copilot <223556219+Copilot@users.noreply.github.com>`

### 5. Pusha

```bash
git push origin main
```

Se il push fallisce per autenticazione, avvisa l'utente: potrebbe dover fare login con `gh auth login`.

### 6. Riporta all'utente

Riassumi in poche righe: commit fatti (hash + titolo), stato del push, cosa resta aperto per la prossima sessione.

## Note

- Gli screenshot in `screenshots/` sono gitignored: non committarli.
- Il database H2 in `biblioteca-personale/data/` non va committato.
- Se la sessione ha prodotto decisioni durature, valuta di aggiornare anche `.github/copilot-instructions.md`.
