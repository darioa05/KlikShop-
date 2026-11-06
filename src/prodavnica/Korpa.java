package prodavnica;

import java.util.ArrayList;

/**
 * Korpa cuva proizvode u kolekciji ArrayList<Proizvod>.
 * Dodavanje u korpu smanjuje stanje na lageru, a uklanjanje ga vraca.
 */
public class Korpa {

    private ArrayList<Proizvod> proizvodi = new ArrayList<>();

    public void dodaj(Proizvod p) {
        if (p == null) {
            throw new IllegalArgumentException("Proizvod ne sme biti null.");
        }
        if (p.getKolicina() == 0) {
            throw new IllegalStateException("Proizvod '" + p.getNaziv() + "' nije na stanju.");
        }
        p.setKolicina(p.getKolicina() - 1);
        proizvodi.add(p);
    }

    public void ukloni(int indeks) {
        if (indeks < 0 || indeks >= proizvodi.size()) {
            throw new IllegalArgumentException("Ne postoji stavka sa tim brojem.");
        }
        Proizvod p = proizvodi.remove(indeks);
        p.setKolicina(p.getKolicina() + 1);
    }

    /** Polimorfizam: za svaki proizvod se poziva izracunajCenu() odgovarajuceg naslednika. */
    public double ukupno() {
        double suma = 0;
        for (Proizvod p : proizvodi) {
            suma += p.izracunajCenu();
        }
        return suma;
    }

    public ArrayList<Proizvod> getProizvodi() {
        return new ArrayList<>(proizvodi);
    }

    public boolean jePrazna() {
        return proizvodi.isEmpty();
    }

    /** Prazni korpu bez vracanja na lager (koristi se pri porucivanju). */
    public void isprazni() {
        proizvodi.clear();
    }
}
