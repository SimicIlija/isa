# Projekat iz predmeta internet softverske arhitekture 

Predmentni projekat iz predmeta Internet softverske arhitekture.

## Članovi tima
* Ilija Simić, RA15/2014
* Smiljana Dragoljević, RA36/2014
* Jelena Stanarević, RA143/2014

### Tehnologije koje se koriste u projektu
* H2 baza podataka
* Java spring framework
* REST servisi
* JavaScript i jQuery
* Bootstrap

Aplikacija je integrisana sa [CircleCI](https://circleci.com/gh/SimicIlija/isa) alatom.

#### Uputstvo za pokretanje:
IDE projekta je IntelliJ IDEA. Nakon dodavanja projekta, potrebno je otvoriti Command Prompt i pozicionirati se na folder u kom se nalazi projekat. Zatim izvršiti sledeće komande:
* ``` mvn clean install ```
* ``` mvn spring-boot:run ```

Server je pokrenut na portu 1234.

Nakon ovih koraka početna stranica se može videti [ovde](http://localhost:1234/index.html).

Baza podataka se može videti [ovde](http://localhost:1234/h2-console).
