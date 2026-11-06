package prodavnica;

/**
 * Apstraktna klasa: roditelj svih proizvoda u prodavnici.
 * Atributi su private, a setteri proveravaju ispravnost podataka (enkapsulacija).
 */
public abstract class Proizvod {

    private String naziv;
    private double osnovnaCena;
    private int kolicina;

    public Proizvod(String naziv, double osnovnaCena, int kolicina) {
        setNaziv(naziv);
        setOsnovnaCena(osnovnaCena);
        setKolicina(kolicina);
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        if (naziv == null || naziv.trim().isEmpty()) {
            throw new IllegalArgumentException("Naziv ne sme biti prazan.");
        }
        this.naziv = naziv.trim();
    }

    public double getOsnovnaCena() {
        return osnovnaCena;
    }

    public void setOsnovnaCena(double osnovnaCena) {
        if (osnovnaCena <= 0) {
            throw new IllegalArgumentException("Cena mora biti veca od 0.");
        }
        this.osnovnaCena = osnovnaCena;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        if (kolicina < 0) {
            throw new IllegalArgumentException("Kolicina ne sme biti negativna.");
        }
        this.kolicina = kolicina;
    }

    /** Apstraktna metoda: svaki naslednik racuna cenu na svoj nacin. */
    public abstract double izracunajCenu();

    /** Naziv kategorije (za prikaz u prozoru); svaki naslednik vraca svoju. */
    public abstract String getKategorija();

    /** Specificni detalji proizvoda (rok, garancija, velicina...); svaki naslednik vraca svoje. */
    public abstract String getDetalji();

    /** Opis proizvoda; naslednici ga redefinisu i dopunjuju. */
    public String opis() {
        return naziv + " | osnovna cena: " + String.format("%.2f", osnovnaCena) + " RSD";
    }

    @Override
    public String toString() {
        return opis() + " | zavrsna cena: " + String.format("%.2f", izracunajCenu()) + " RSD | na stanju: " + kolicina;
    }
}
