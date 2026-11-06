package prodavnica;

/**
 * Interfejs: proizvodi koji mogu imati popust.
 * Ne implementiraju ga svi proizvodi (npr. Elektronika nema popust),
 * zato u Main-u proveravamo sa instanceof.
 */
public interface Popust {

    /** Postavlja popust u procentima (0-90). */
    void primeniPopust(double procenat);

    /** Vraca trenutni popust u procentima. */
    double getPopust();
}
