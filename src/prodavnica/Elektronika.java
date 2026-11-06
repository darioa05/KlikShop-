package prodavnica;

/**
 * Naslednik klase Proizvod. Elektronika ima garanciju koja povecava cenu.
 * Ne implementira Popustljiv.
 */
public class Elektronika extends Proizvod {

    private int garancijaMeseci;

    public Elektronika(String naziv, double osnovnaCena, int kolicina, int garancijaMeseci) {
        super(naziv, osnovnaCena, kolicina);
        setGarancijaMeseci(garancijaMeseci);
    }

    public int getGarancijaMeseci() {
        return garancijaMeseci;
    }

    public void setGarancijaMeseci(int garancijaMeseci) {
        if (garancijaMeseci < 0) {
            throw new IllegalArgumentException("Garancija ne sme biti negativna.");
        }
        this.garancijaMeseci = garancijaMeseci;
    }

    /** Osnovna cena plus 300 RSD za svakih 12 meseci garancije. */
    @Override
    public double izracunajCenu() {
        return getOsnovnaCena() + 300.0 * (garancijaMeseci / 12.0);
    }

    @Override
    public String getKategorija() {
        return "Elektronika";
    }

    @Override
    public String getDetalji() {
        return "Garancija: " + garancijaMeseci + " meseci";
    }

    @Override
    public String opis() {
        return "[ELEKTRONIKA] " + super.opis() + " | garancija: " + garancijaMeseci + " meseci";
    }
}
