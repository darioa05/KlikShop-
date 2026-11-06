package prodavnica;

import java.util.ArrayList;

/**
 * Kupac ima svoju korpu i listu narudzbina.
 */
public class Kupac {

    private String ime;
    private Korpa korpa = new Korpa();
    private ArrayList<Narudzbina> narudzbine = new ArrayList<>();

    public Kupac(String ime) {
        setIme(ime);
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        if (ime == null || ime.trim().isEmpty()) {
            throw new IllegalArgumentException("Ime ne sme biti prazno.");
        }
        this.ime = ime.trim();
    }

    public Korpa getKorpa() {
        return korpa;
    }

    public ArrayList<Narudzbina> getNarudzbine() {
        return new ArrayList<>(narudzbine);
    }

    /** Pravi narudzbinu od sadrzaja korpe i prazni korpu. */
    public Narudzbina naruci() {
        if (korpa.jePrazna()) {
            throw new IllegalStateException("Korpa je prazna.");
        }
        Narudzbina n = new Narudzbina(korpa.getProizvodi());
        narudzbine.add(n);
        korpa.isprazni();
        return n;
    }
}
