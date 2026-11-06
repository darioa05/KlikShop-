package prodavnica;
/**
 * Naslednik klase Proizvod. Hrana ima rok trajanja i moze imati popust.
 */
public class Hrana extends Proizvod implements Popust {

    private int danaDoIsteka;
    private double popust;

    public Hrana(String naziv, double osnovnaCena, int kolicina, int danaDoIsteka) {
        super(naziv, osnovnaCena, kolicina);
        setDanaDoIsteka(danaDoIsteka);
    }

    public int getDanaDoIsteka() {
        return danaDoIsteka;
    }

    public void setDanaDoIsteka(int danaDoIsteka) {
        if (danaDoIsteka < 0) {
            throw new IllegalArgumentException("Rok trajanja ne sme biti negativan.");
        }
        this.danaDoIsteka = danaDoIsteka;
    }

    @Override
    public void primeniPopust(double procenat) {
        if (procenat < 0 || procenat > 90) {
            throw new IllegalArgumentException("Popust mora biti izmedju 0 i 90 procenata.");
        }
        this.popust = procenat;
    }

    @Override
    public double getPopust() {
        return popust;
    }

    /** Osnovna cena umanjena za popust; ako rok istice za 3 dana ili manje, jos 30% jeftinije. */
    @Override
    public double izracunajCenu() {
        double cena = getOsnovnaCena() * (1 - popust / 100);
        if (danaDoIsteka <= 3) {
            cena = cena * 0.70;
        }
        return cena;
    }

    @Override
    public String getKategorija() {
        return "Hrana";
    }

    @Override
    public String getDetalji() {
        String detalji = "Rok: " + danaDoIsteka + " dana";
        if (danaDoIsteka <= 3) {
            detalji += "  |  uskoro isti\u010de, dodatnih 30% jeftinije";
        }
        if (popust > 0) {
            detalji += "  |  popust " + String.format("%.0f", popust) + "%";
        }
        return detalji;
    }

    @Override
    public String opis() {
        return "[HRANA] " + super.opis() + " | rok: " + danaDoIsteka + " dana | popust: " + popust + "%";
    }
}
