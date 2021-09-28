# Wprowadzenie do R2DBC
##Wrocław JUG 29 września 2021

Slajdy dostępne pod są [tutaj](https://slides.com/koziolek/wprowadzenie-do-r2dbc/fullscreen).

### Wymagania

Do uruchomienia kodu potrzebujesz Javy 17 i `docker-compose`.
Projekt budowany jest mavenem.

### Konfiguracja

Uruchom bazę danych za pomocą:

```bash
docker-compose up -d
```

Baza będzie wystawiona na `localhost:15432`. Możesz zmienić port w pliku [`docker-compose.yml`](https://github.com/Koziolek/wrug-r2jbc/blob/c154cf87a53430bc466130491876711be5594542/docker-compose.yml#L9).
Do bazy masz dwóch użytkownikaów `jpa`/`jpa` i `r2dbc`/`r2dbc`. Pierwszy służy do łączenia się z aplikacji JPA, drugi do reszty połączeń. 
Główne hasło do bazy to `asdf`. 

