package prodavnica;

import java.util.ArrayList;

/**
 * Prodavnica cuva ponudu proizvoda u kolekciji ArrayList<Proizvod>.
 * Promenljiva je roditeljskog tipa, a objekti su Hrana, Elektronika, Odeca.
 */
public class Prodavnica {

    private String naziv;
    private ArrayList<Proizvod> proizvodi = new ArrayList<>();

    public Prodavnica(String naziv) {
        if (naziv == null || naziv.trim().isEmpty()) {
            throw new IllegalArgumentException("Naziv prodavnice ne sme biti prazan.");
        }
        this.naziv = naziv.trim();
    }

    public String getNaziv() {
        return naziv;
    }

    public void dodajProizvod(Proizvod p) {
        if (p == null) {
            throw new IllegalArgumentException("Proizvod ne sme biti null.");
        }
        proizvodi.add(p);
    }

    public Proizvod getProizvod(int indeks) {
        if (indeks < 0 || indeks >= proizvodi.size()) {
            throw new IllegalArgumentException("Ne postoji proizvod sa tim brojem.");
        }
        return proizvodi.get(indeks);
    }

    public ArrayList<Proizvod> getProizvodi() {
        return new ArrayList<>(proizvodi);
    }

    /**
     * Primenjuje popust na sve proizvode koji implementiraju interfejs Popustljiv.
     * Vraca koliko je proizvoda dobilo popust.
     */
    public int primeniPopustNaSve(double procenat) {
        int broj = 0;
        for (Proizvod p : proizvodi) {
            if (p instanceof Popust) {
                ((Popust) p).primeniPopust(procenat);
                broj++;
            }
        }
        return broj;
    }
}
