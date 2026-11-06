package prodavnica;

import java.util.ArrayList;

/**
 * Narudzbina je "snimak" korpe u trenutku porucivanja.
 */
public class Narudzbina {

    private static int brojac = 1;

    private int id;
    private ArrayList<Proizvod> proizvodi;
    private double ukupnaCena;

    public Narudzbina(ArrayList<Proizvod> proizvodi) {
        if (proizvodi == null || proizvodi.isEmpty()) {
            throw new IllegalArgumentException("Narudzbina mora imati bar jedan proizvod.");
        }
        this.id = brojac++;
        this.proizvodi = new ArrayList<>(proizvodi);
        double suma = 0;
        for (Proizvod p : this.proizvodi) {
            suma += p.izracunajCenu();
        }
        this.ukupnaCena = suma;
    }

    public int getId() {
        return id;
    }

    public double getUkupnaCena() {
        return ukupnaCena;
    }

    public ArrayList<Proizvod> getProizvodi() {
        return new ArrayList<>(proizvodi);
    }

    @Override
    public String toString() {
        return "Narudzbina #" + id + " | stavki: " + proizvodi.size()
                + " | ukupno: " + String.format("%.2f", ukupnaCena) + " RSD";
    }
}
