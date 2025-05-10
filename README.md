# 🗳️ Kontrola-izbora
Cilj projekta je da se omogući ednostavna, pouzdana i bezbedna kontrola izbornog procesa putem modernih digitalnih alata.

## 📌 Opis projekta

Ova aplikacija služi za digitalnu koordinaciju i praćenje izbornog dana preko Discord platforme. Korišćenjem Discord servera, botova i Spring Boot mikroservisa, omogućava se kontrolorima i koordinatorima efikasna komunikacija, prijava izlaznosti, slanje slika zapisnika i izveštavanje o nepravilnostima.

## 🎯 Ciljevi

- Ubrzati i olakšati komunikaciju među izbornim timovima.
- Automatizovati unos i skladištenje podataka o izlaznosti i rezultatima glasanja.
- Omogućiti prijavu nepravilnosti sa prilozima (fotografije, tekst).
- Prikupiti sve podatke u centralni sistem za dalju analizu i izveštavanje.

## 🤖 Zašto Discord?

Discord je moderna, skalabilna i sigurna platforma, pogodna za rad u realnom vremenu:
- Multinacionalna i dokazano pouzdana kompanija
- Lako podešavanje servera i uloga
- Integracija botova i API-ja za automatizaciju
- Visok nivo sigurnosti i otpornosti na upade
- Idealna za tekstualnu i glasovnu komunikaciju, ali i za slanje dokumenata i slika

## 🏗️ Arhitektura sistema

- **Discord server**: za komunikaciju timova i slanje podataka
- **Discord bot**: služi za prikupljanje podataka i komandovanje
- **Spring Boot mikroservisi**: svaka opština ima svoj servis i bazu
- **MySQL baza**: skladišti podatke lokalno ili u oblaku
- **Cloud servis za slike**: za čuvanje fotografija zapisnika i nepravilnosti
- **Centralna aplikacija**: agregira podatke i prikazuje rezultate

## 🧰 Korišćene tehnologije

- Java 17+
- Spring Boot
- MySQL / MariaDB
- Discord Java API (JDA)
- Docker (planirano)
- Cloud servis za slike (npr. Cloudinary, AWS S3 – u planu)

## 🛣️ Plan razvoja

1. Definisanje okruženja (Discord server, Git repozitorijum)
2. Kreiranje lokalne baze i povezivanje sa Spring aplikacijom
3. Izrada osnovnog Discord bota
4. Testiranje komandi i upisa podataka
5. Razrada CRUD operacija i REST API-ja
6. Priprema cloud servisa i produkcionog okruženja
7. Centralizacija podataka i UI za prikaz

