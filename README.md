# Student2Students

## Koji problem resava aplikacija
Student2Students je aplikacija koja povezuje studente koji imaju jako dobro domensko znanje iz odredjene oblasti sa studentima kojima je pomoc potrebna pri savladjivanju gradiva. Ideja je nastala iz cinjenice da studentima najbolje mogu da predoce neki novi pojam upravo njihove kolege, studenti.

Osim ucenja, vazan deo studentskog zivota je i upoznavanje velikog broja kolega istih ili slicnih studijskih usmerenja sirom sveta. Taj izazov se prevazilazi razmenom studenata (Exchange entitet). Student moze da trazi razmenu iz vec postojece kolekcije aktivnih razmena ili da sam predlaze razmenu drugim studentima pri tom navodeci drzavu, univerzitet, trajanje, datum, cenu...

## Kako funkcionise aplikacija
Svaki Post (odnosno svako pitanje) ima tacno jedan Major (oblast na koju se odnosi) i ima kolekciju Topica, odnosno kljucnih reci.

<blockquote>Pitanje ima Major "Computer Science", a kolekciju Topic-a: "BackEnd", "NodeJS", "Docker", "MySQL"... </blockquote>

Ukoliko neko od clanova platforme odgovori na pitanje korisniku, tog momenta se korisniku salje automatski generisan mejl sa porukom da mu je odredjeni korisnik odgovorio na pitanje. Time se postize veca aktivnost svih korisnika platforme, kao i smanjena mogucnost da korisnik ne primeti da mu je problem resen.

Svim entitetima aplikacije upravlja administrator. Dodaje nove oblasti iz kojih se mogu postavljati pitanja, nove kljucne reci... Razlog za ovakvu striktnu konstukciju je sprecavanje spamova, kao i laksa pretraga relevantnih pitanja. Ukoliko aministrator primeti neprimereno koriscenje platforme, moze obrisati ceo Major, tako da se sva pitanja koja su bila vezana za tu oblast se automatski brisu.

Takodje, administrator dodaje i univerzitete. Student prilikom podnosenja zahteva za razmenu studenata bira odredjeni univerzitet iz liste univerziteta koju je prethodno administrator odobrio. Odnosno, univerzitete sa kojim platforma ima ugovorenu saradnju.

Postavljanje zahteva za razmenu studenata je omoguce samo registrovanim korisnicima, dok je pregled pitanja omogucen svim posetiocia platforme.

## Kako pokrenuti aplikaciju

Aplikacija se sastoji iz dva dela.
- Front End - ReactJS
- Back End - Mikroservisna arhitektura Spring Boot aplikacija

Pre pokretanja aplikacije, uveriti se da su slobodni portovi
- <code>8080</code>
- <code>8761</code>
- <code>5672</code>
- <code>15672</code>

Kako bi proces pokretanja velikog broja aplikacija bilo sto jednostavniji, kreiran je docker-compose fajl. Napomena: Ukoliko nemate instaliran docker, potrebno ga je instalirati.

Pozicionirati se u <code>Student2Students/Back-end</code> dikretorijum i izvrsiti komandu
```
docker-compose up --build -d
```

Sacekati nekoliko minuta da se podignu sve aplikacije. Korisna komanda za proveru da li se kontejner i dalje pokrece ili je vec pokrenut:
```
docker logs zuul-proxy-spring
```
Ako je neko vreme isti log, znaci da je aplikacija podignuta.

Zatim, podici Front-End deo komandom

```
yarn start
```
ili
```
npm install
npm start
```
Po pokrtanju aplikacije otici na <code>http://localhost:3000</code>

## Kako koristiti aplikaciju

Nakon sto je aplikacija pokrenuta, napraviti novi nalog. Napomena: mora se uneti validna email adresa, kako bi se potvrdio verifikacioni link u roku od 15 minuta od procesa registracije.

Postoje i predefinisani korisnici:

| Username   |      Password      | Role |
|----------|:-------------:|------:|
| admin |  admin | ADMIN |
| ivan91 |    ivan91   |   STUDENT |
| linus69 | linus69 |    STUDENT |

## Kako je strukturirana aplikacija

Back end aplikacije sastoji se iz sest Spring Boot aplikacija
- Zuul - Proxy/Gateway. Vodi racuna o autentikaciji korisnika koji pokusavaju da pristupe sistemu. Vodi racuna o rutiranju zahteva ka svakom mikroservisu posebno. Pri cemu svaka od aplikacija radi autorizaciju nezazvisno.
- Eureka - Discovery Service - Aplikacija u kojoj su registrovani svi servisi kako bi se izbeglo hardkodovanje putanje do servisa
- Student2Students - Aplikacija koja manipulise entitetima kao sto su Major, Topic, Country...
- Posting-Service - Aplikacija zaduzena za rad sa Post, Exchange, Comment...
- Image-Service - Aplikacija koja podrzava rad sa fotografijama

## Bezbednost

Koriscenjem Spring Security radnog okvira generise se JWT koji se na klijentskoj strani cuva u Http-Only cookie, kako bi opasnost od kradje tokena bila svedena na minimum. Sa svakim zahtevom koji pristupa sticenom resursu salje se token koji sadrzi rolu i korisnicko ime. Token se ne cuva na serverskoj strani, vec je potpuno stateless pristup.

## Komunikacija izmedju servisa

Servisi medjusobno komuniciraju asinhrono razmenom poruka preko Rabbitmq Message Broker-a. Broker je konfigurisan tako da ukoliko jedan servis padne, poruka koja se nalazi u queue ce se sacuvati da bi prilikom podizanja servisa aplikacija procesirala neobradjenu poruku. Restartovanje servisa se radi automatski i taj deo je konfigurisan u Docker fajlu. Na taj nacin podignuta je robusnost kompletnog sistema. Takodje, konzistentnost izmedju baza podataka razlicitih servisa je znatno poboljsana, buduci da svaki servis ima zasebnu instancu baze podataka.

## Tehnologije koriscene
- Java 8
- React.js
- Spring Boot
- Spring Security
- Hibernate
- Rabbitmq
- Docker
- MySQL
