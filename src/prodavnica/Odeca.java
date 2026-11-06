package prodavnica;

/**
 * Naslednik klase Proizvod. Odeca ima velicinu, moze biti na sezonskom snizenju
 * i moze imati popust.
 */
public class Odeca extends Proizvod implements Popust {

    private String velicina;
    private boolean sezonskoSnizenje;
    private double popust;

    public Odeca(String naziv, double osnovnaCena, int kolicina, String velicina, boolean sezonskoSnizenje) {
        super(naziv, osnovnaCena, kolicina);
        setVelicina(velicina);
        this.sezonskoSnizenje = sezonskoSnizenje;
    }

    public String getVelicina() {
        return velicina;
    }

    public void setVelicina(String velicina) {
        if (velicina == null || velicina.trim().isEmpty()) {
            throw new IllegalArgumentException("Velicina ne sme biti prazna.");
        }
        this.velicina = velicina.trim().toUpperCase();
    }

    public boolean isSezonskoSnizenje() {
        return sezonskoSnizenje;
    }

    public void setSezonskoSnizenje(boolean sezonskoSnizenje) {
        this.sezonskoSnizenje = sezonskoSnizenje;
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

    /** Osnovna cena umanjena za popust; ako je sezonsko snizenje, jos 20% jeftinije. */
    @Override
    public double izracunajCenu() {
        double cena = getOsnovnaCena() * (1 - popust / 100);
        if (sezonskoSnizenje) {
            cena = cena * 0.80;
        }
        return cena;
    }

    @Override
    public String getKategorija() {
        return "Ode\u0107a";
    }

    @Override
    public String getDetalji() {
        String detalji = "Veli\u010dina: " + velicina;
        if (sezonskoSnizenje) {
            detalji += "  |  sezonsko sni\u017eenje -20%";
        }
        if (popust > 0) {
            detalji += "  |  popust " + String.format("%.0f", popust) + "%";
        }
        return detalji;
    }

    @Override
    public String opis() {
        return "[ODECA] " + super.opis() + " | velicina: " + velicina
                + (sezonskoSnizenje ? " | SEZONSKO SNIZENJE" : "") + " | popust: " + popust + "%";
    }
}
