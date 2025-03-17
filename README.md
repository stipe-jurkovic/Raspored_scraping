# Kotlin Timetable Fetcher

Ovaj projekt dohvaća raspored događaja za korisnike pomoću HTTP klijenta OkHttp.

## Zahtjevi

Prije pokretanja projekta, osiguraj da imaš instalirane sljedeće alate:
- [JDK 11+](https://adoptopenjdk.net/)
- [Gradle](https://gradle.org/install/)
- [Kotlin](https://kotlinlang.org/) CLI ili IDE poput Intellij IDEA

## Instalacija i pokretanje

1. Kloniraj repozitorij:
   ```sh
   git clone <URL_REPOZITORIJA>
   cd <NAZIV_PROJEKTA>
   ```

2. Dodaj korisnička imena u `data/usernames.txt` (jedno po liniji):
   ```
   abbbbb00
   user123
   testuser
   ```

3. Dodaj početni i krajnji datum u `data/dates.txt` (jedan datum po liniji, u formatu `MM-DD-YYYY`):
   ```
   02-24-2024
   06-06-2024
   ```

4. Pokreni aplikaciju:
   ```sh
   ./gradlew run
   ```

## Struktura projekta

- `Main.kt` - Ulazna točka aplikacije, dohvaća raspored za zadane korisnike i sprema podatke u CSV.
- `TimetableService.kt` - Klasa koja upravlja HTTP pozivima za dohvaćanje rasporeda.
- `TimeTableRepository.kt` - Repozitorij koji koristi `TimetableService` za dohvaćanje podataka.
- `data/usernames.txt` - Datoteka s popisom korisničkih imena.
- `data/dates.txt` - Datoteka s početnim i krajnjim datumom.
- `data/timetables/` - Direktorij u koji se spremaju CSV datoteke.
- `build.gradle.kts` - Konfiguracija projekta i ovisnosti.

## Konfiguracija

- Korisnički podaci se čitaju iz `data/usernames.txt`.
- Početni i krajnji datum se čitaju iz `data/dates.txt`.
- Za svakog korisnika generira se CSV datoteka u `data/timetables/` u obliku `<username>_<startDate>_to_<endDate>.csv`.

## Format CSV datoteke

Svaka generirana CSV datoteka sadrži sljedeće stupce:
```
Date,Event Name,Location
03-01-2024,Predavanje Matematika,Dvorana A
03-03-2024,Lab Vježbe Programiranje,Lab 3
```

## Dodatne informacije

- OkHttp se koristi za slanje HTTP zahtjeva.
- `suspend` funkcija se koristi zbog korištenja Kotlin Coroutines.
- Ako `data/timetables/` ne postoji, aplikacija će ga automatski kreirati.
- Ako `data/usernames.txt` ili `data/dates.txt` nisu pravilno konfigurirani, aplikacija će baciti grešku.

Ako imaš bilo kakvih problema, provjeri ovisnosti i verzije alata koje koristiš.

