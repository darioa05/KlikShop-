# Prodavnica

Aplikacija sa grafičkim interfejsom (Java Swing) koja simulira jednostavnu prodavnicu. Kupac pregleda ponudu (hrana, elektronika, odeća), dodaje proizvode u korpu, uklanja ih i poručuje. Projekat je izrađen za predmet Objektno orijentisano programiranje i demonstrira osnovne principe OOP-a.

## Kako se pokreće

**Eclipse:**
1. Napravite novi Java Project i kopirajte sadržaj foldera `src` u njegov `src` folder (ili `File > Import > Existing Projects into Workspace`).
2. Desni klik na `Main.java` > `Run As > Java Application`.


## Primena OOP principa

- **Enkapsulacija**
  - Svi atributi su `private`.
  - Setteri proveravaju podatke (npr. cena > 0, naziv nije prazan).
- **Nasleđivanje**
  - `Proizvod` -> `Hrana`, `Elektronika`, `Odeca`
- **Polimorfizam**
  - Metode `izracunajCenu()`, `opis()`, `getKategorija()` i `getDetalji()` redefinisane su sa `@Override` u naslednicima.
  - Pozivaju se preko promenljive tipa `Proizvod`, npr. u `Korpa.ukupno()` i u klasama `PonudaRenderer` i `KorpaRenderer` (u `ProdavnicaGUI`).
- **Apstrakcija**
  - Apstraktna klasa: `Proizvod`
  - Interfejs: `Popust` (implementiraju ga `Hrana` i `Odeca`)
- **Kolekcije**
  - `ArrayList<Proizvod>` u klasama `Prodavnica` i `Korpa`
  - `ArrayList<Narudzbina>` u klasi `Kupac`

## Klase

- `Proizvod` (apstraktna), `Hrana`, `Elektronika`, `Odeca`
- `Popustv` (interfejs)
- `Korpa`, `Kupac`, `Narudzbina`, `Prodavnica`
- `ProdavnicaGUI` (grafički prozor, Swing)
- `Main` (pokreće prozor)
