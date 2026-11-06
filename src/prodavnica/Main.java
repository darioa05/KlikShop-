package prodavnica;

import javax.swing.SwingUtilities;

/**
 * Ulazna tacka programa: pokrece graficki prozor prodavnice.
 */
public class Main {

    public static void main(String[] args) {
        // Glatkiji prikaz teksta
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        // Swing prozori se prave na posebnoj niti za grafiku (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> new ProdavnicaGUI().setVisible(true));
    }
}
