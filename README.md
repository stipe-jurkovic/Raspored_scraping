# Kotlin Timetable Fetcher

Ovaj projekt dohvaća raspored događaja za korisnike pomoću HTTP klijenta OkHttp.

### Najbolje je pokrenuti koristeci IDE poput Intellij IDEA

## Zahtjevi

Prije pokretanja projekta, osiguraj da imaš instalirane sljedeće alate:
- [JDK 11+](https://adoptopenjdk.net/)


## Instalacija i pokretanje bez IDE-a

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
id, name, shortName, colorId, professor, eventType, groups, classroom, start, end, description, recurring, recurringType, recurringUntil, studyCode
532053,Kriptografija i mrežna sigurnost,KIMS,0,Čagalj Mario,PREDAVANJE,,C501,2024-02-26T10:15,2024-02-26T12:00,ponedjeljak 26.2.2024. 10:15 - 12:00 (2 sata),true,WEEKLY,do 9.6.2024.,250
532137,Podržano strojno učenje,PSU,0,Vasilj Josip,PREDAVANJE,,A243,2024-02-27T08:15,2024-02-27T10:00,utorak 27.2.2024. 8:15 - 10:00 (2 sata),true,WEEKLY,do 9.6.2024.,250
532077,Metode optimizacije,MO,0,Marasović Jadranka,PREDAVANJE,,C502,2024-02-27T10:15,2024-02-27T12:00,utorak 27.2.2024. 10:15 - 12:00 (2 sata),true,WEEKLY,do 9.6.2024.,250

```

## Dodatne informacije

- OkHttp se koristi za slanje HTTP zahtjeva.
- `suspend` funkcija se koristi zbog korištenja Kotlin Coroutines.
- Ako `data/timetables/` ne postoji, aplikacija će ga automatski kreirati.
- Ako `data/usernames.txt` ili `data/dates.txt` nisu pravilno konfigurirani, aplikacija će baciti grešku.

Ako imaš bilo kakvih problema, provjeri ovisnosti i verzije alata koje koristiš.

