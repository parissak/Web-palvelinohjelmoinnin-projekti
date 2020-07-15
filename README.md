# Web-palvelinohjelmoinnin-projekti

## Tarkoitus
Helsingin yliopiston kurssin [Web-palvelinohjelmointi Java 2020](https://web-palvelinohjelmointi-20.mooc.fi/projekti) projektityö, minkä aiheena on ollut "ansioluettelo- ja työntekijähakusovellus eli tuttavallisemmin vanhan kansan LinkedIn".

Tehty Springia, Thymeleafia ja Twitter Bootstrapia käyttäen.

## Toteutetut toiminnallisuudet
* Käyttäjien rekisteröityminen
* Käyttäjien etsiminen ja yhdistäminen
* Yhteyksien tarkastelu, hyväksyminen ja purkaminen
* Profiilikuvan lisääminen ja poistaminen
* Henkilökohtainen etusivu
* Postaaminen yhteiselle sivulle
* Postausten tykkääminen ja kommentoiminen

## Sovelluksen puuttuvat ominaisuudet ja ongelmakohdat
* N+1 -kyselyn ongelmat (ainakin postauksien ja niiden kommenttien näyttämisessä)
* Osaa syötteistä ei ole validoitu
* Testien puuttuminen

## Sovellus verkossa
Sovellus sijaitsee myös [Herokun palvelussa](https://intense-harbor-77342.herokuapp.com/).
