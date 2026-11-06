package prodavnica;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Graficki prozor prodavnice (Swing). Sva logika je u klasama modela
 * (Prodavnica, Kupac, Korpa, Proizvod...), a ovde se samo prikazuje i poziva.
 *
 * Napomena: slova c, c, s, z, dj sa kvacicama u tekstu pisana su kao \\uXXXX
 * kodovi (npr. \\u010d), da se ne pokvare u Eclipse-u zbog kodiranja fajla.
 */
public class ProdavnicaGUI extends JFrame {

    // ---- Boje ----
    private static final Color POZADINA = new Color(0xF4F6F8);
    private static final Color ZAGLAVLJE = new Color(0x2C3E50);
    private static final Color LINIJA = new Color(0xDDE2E7);
    private static final Color SELEKCIJA = new Color(0xDCEBFA);
    private static final Color TEKST = new Color(0x2C3E50);
    private static final Color SIVA = new Color(0x7F8C8D);
    private static final Color ZELENA = new Color(0x27AE60);
    private static final Color CRVENA = new Color(0xE74C3C);
    private static final Color NARANDZASTA = new Color(0xE67E22);
    private static final Color PLAVA = new Color(0x2980B9);
    private static final Color TAMNA = new Color(0x34495E);

    private Prodavnica prodavnica;
    private Kupac kupac;

    // Modeli lista: Swing liste prikazuju sadrzaj ovih modela
    private DefaultListModel<Proizvod> ponudaModel = new DefaultListModel<>();
    private DefaultListModel<Proizvod> korpaModel = new DefaultListModel<>();
    private JList<Proizvod> ponudaLista = new JList<>(ponudaModel);

    // Lista korpe sa natpisom "Korpa je prazna" kad nema stavki
    private JList<Proizvod> korpaLista = new JList<Proizvod>(korpaModel) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getModel().getSize() == 0) {
                String tekst = "Korpa je prazna";
                g.setFont(new Font("SansSerif", Font.PLAIN, 14));
                g.setColor(SIVA);
                FontMetrics fm = g.getFontMetrics();
                g.drawString(tekst, (getWidth() - fm.stringWidth(tekst)) / 2, getHeight() / 2);
            }
        }
    };

    private JLabel ukupnoLabel = new JLabel("0.00 RSD");
    private JLabel korpaInfoLabel = new JLabel();

    public ProdavnicaGUI() {
        super("KlikShop");
        prodavnica = new Prodavnica("KlikShop");
        popuniPonudu();
        kupac = pitajZaKupca();

        setTitle(prodavnica.getNaziv() + " - kupac: " + kupac.getIme());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(POZADINA);
        setLayout(new BorderLayout());

        add(napraviZaglavlje(), BorderLayout.NORTH);
        add(napraviSadrzaj(), BorderLayout.CENTER);

        osveziPrikaz();
        setSize(1050, 620);
        setMinimumSize(new Dimension(850, 500));
        setLocationRelativeTo(null);
    }

    // ------------------------------------------------------------------
    // Priprema podataka
    // ------------------------------------------------------------------

    private void popuniPonudu() {
        prodavnica.dodajProizvod(new Hrana("Mleko 1l", 150, 20, 2));
        prodavnica.dodajProizvod(new Hrana("\u010cokolada", 220, 15, 90));
        prodavnica.dodajProizvod(new Elektronika("Slu\u0161alice", 4500, 5, 24));
        prodavnica.dodajProizvod(new Elektronika("Punja\u010d", 1800, 8, 12));
        prodavnica.dodajProizvod(new Odeca("Majica", 2000, 10, "m", true));
        prodavnica.dodajProizvod(new Odeca("Farmerke", 5500, 3, "l", false));
    }

    private Kupac pitajZaKupca() {
        while (true) {
            String ime = JOptionPane.showInputDialog(null, "Unesite Va\u0161e ime:", "Dobrodo\u0161li",
                    JOptionPane.QUESTION_MESSAGE);
            if (ime == null) {
                System.exit(0); // korisnik je kliknuo Cancel
            }
            try {
                return new Kupac(ime); // setIme proverava da ime nije prazno
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ------------------------------------------------------------------
    // Izgradnja prozora
    // ------------------------------------------------------------------

    private JPanel napraviZaglavlje() {
        JLabel naslov = new JLabel(prodavnica.getNaziv());
        naslov.setFont(new Font("SansSerif", Font.BOLD, 24));
        naslov.setForeground(Color.WHITE);

        JLabel kupacLabel = new JLabel("Kupac: " + kupac.getIme());
        kupacLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        kupacLabel.setForeground(Color.WHITE);
        kupacLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        korpaInfoLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        korpaInfoLabel.setForeground(new Color(0xBDC3C7));
        korpaInfoLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel desno = new JPanel(new GridLayout(2, 1));
        desno.setOpaque(false);
        desno.add(kupacLabel);
        desno.add(korpaInfoLabel);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ZAGLAVLJE);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        panel.add(naslov, BorderLayout.WEST);
        panel.add(desno, BorderLayout.EAST);
        return panel;
    }

    private JPanel napraviSadrzaj() {
        JPanel panel = new JPanel(new BorderLayout(16, 12));
        panel.setBackground(POZADINA);
        panel.setBorder(BorderFactory.createEmptyBorder(16, 20, 16, 20));
        panel.add(napraviPanelPonude(), BorderLayout.CENTER);
        panel.add(napraviPanelKorpe(), BorderLayout.EAST);
        panel.add(napraviDonjiPanel(), BorderLayout.SOUTH);
        return panel;
    }

    private JPanel napraviPanelPonude() {
        ponudaLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ponudaLista.setCellRenderer(new PonudaRenderer());
        ponudaLista.setToolTipText("Dvoklik dodaje proizvod u korpu");
        ponudaLista.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    dodajUKorpu();
                }
            }
        });

        JButton dodajDugme = napraviDugme("Dodaj u korpu", ZELENA);
        dodajDugme.addActionListener(e -> dodajUKorpu());

        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setOpaque(false);
        panel.add(napraviNaslovSekcije("Ponuda"), BorderLayout.NORTH);
        panel.add(napraviScroll(ponudaLista), BorderLayout.CENTER);
        panel.add(dodajDugme, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel napraviPanelKorpe() {
        korpaLista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        korpaLista.setCellRenderer(new KorpaRenderer());

        JButton ukloniDugme = napraviDugme("Ukloni iz korpe", CRVENA);
        ukloniDugme.addActionListener(e -> ukloniIzKorpe());

        JLabel ukupnoNaslov = new JLabel("Ukupno:");
        ukupnoNaslov.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ukupnoNaslov.setForeground(SIVA);
        ukupnoLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        ukupnoLabel.setForeground(new Color(0x1E8449));
        ukupnoLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JPanel ukupnoPanel = new JPanel(new BorderLayout());
        ukupnoPanel.setOpaque(false);
        ukupnoPanel.add(ukupnoNaslov, BorderLayout.WEST);
        ukupnoPanel.add(ukupnoLabel, BorderLayout.CENTER);

        JPanel dole = new JPanel(new GridLayout(2, 1, 0, 8));
        dole.setOpaque(false);
        dole.add(ukupnoPanel);
        dole.add(ukloniDugme);

        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(330, 0));
        panel.add(napraviNaslovSekcije("Korpa"), BorderLayout.NORTH);
        panel.add(napraviScroll(korpaLista), BorderLayout.CENTER);
        panel.add(dole, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel napraviDonjiPanel() {
        JButton popustDugme = napraviDugme("Primeni popust", PLAVA);
        popustDugme.addActionListener(e -> primeniPopust());

        JButton narudzbineDugme = napraviDugme("Moje narud\u017ebine", TAMNA);
        narudzbineDugme.addActionListener(e -> prikaziNarudzbine());

        JButton poruciDugme = napraviDugme("Poru\u010di", ZELENA);
        poruciDugme.addActionListener(e -> poruci());

        JPanel levo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        levo.setOpaque(false);
        levo.add(popustDugme);

        JPanel desno = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        desno.setOpaque(false);
        desno.add(narudzbineDugme);
        desno.add(poruciDugme);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(levo, BorderLayout.WEST);
        panel.add(desno, BorderLayout.EAST);
        return panel;
    }

    // ------------------------------------------------------------------
    // Pomocne metode za izgled
    // ------------------------------------------------------------------

    private JButton napraviDugme(String tekst, Color boja) {
        JButton dugme = new JButton(tekst);
        dugme.setBackground(boja);
        dugme.setForeground(Color.WHITE);
        dugme.setFont(new Font("SansSerif", Font.BOLD, 13));
        dugme.setFocusPainted(false);
        dugme.setContentAreaFilled(false); // sami crtamo pozadinu...
        dugme.setOpaque(true);             // ...pa boja ista na svakom sistemu
        dugme.setBorder(BorderFactory.createEmptyBorder(10, 18, 10, 18));
        dugme.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        dugme.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                dugme.setBackground(boja.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                dugme.setBackground(boja);
            }
        });
        return dugme;
    }

    private JLabel napraviNaslovSekcije(String tekst) {
        JLabel label = new JLabel(tekst);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(TEKST);
        return label;
    }

    private JScrollPane napraviScroll(JList<Proizvod> lista) {
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createLineBorder(LINIJA));
        scroll.getViewport().setBackground(Color.WHITE);
        return scroll;
    }

    private static String rsd(double iznos) {
        return String.format("%.2f RSD", iznos);
    }

    private static Color bojaKategorije(String kategorija) {
        switch (kategorija) {
            case "Hrana":
                return NARANDZASTA;
            case "Elektronika":
                return PLAVA;
            case "Ode\u0107a":
                return new Color(0x9B59B6);
            default:
                return SIVA;
        }
    }

    // ------------------------------------------------------------------
    // Prikaz jednog reda u listama (renderer)
    // ------------------------------------------------------------------

    /** Red u listi ponude: traka boje kategorije, naziv, detalji, cena i stanje. */
    private class PonudaRenderer extends JPanel implements ListCellRenderer<Proizvod> {
        private JPanel traka = new JPanel();
        private JLabel naziv = new JLabel();
        private JLabel detalji = new JLabel();
        private JLabel cena = new JLabel();
        private JLabel stanje = new JLabel();

        PonudaRenderer() {
            setLayout(new BorderLayout(12, 0));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, LINIJA),
                    BorderFactory.createEmptyBorder(8, 0, 8, 12)));

            traka.setPreferredSize(new Dimension(6, 0));

            naziv.setFont(new Font("SansSerif", Font.BOLD, 15));
            detalji.setFont(new Font("SansSerif", Font.PLAIN, 12));
            cena.setFont(new Font("SansSerif", Font.PLAIN, 15));
            cena.setHorizontalAlignment(SwingConstants.RIGHT);
            stanje.setFont(new Font("SansSerif", Font.PLAIN, 12));
            stanje.setHorizontalAlignment(SwingConstants.RIGHT);

            JPanel tekst = new JPanel(new GridLayout(2, 1));
            tekst.setOpaque(false);
            tekst.add(naziv);
            tekst.add(detalji);

            JPanel desno = new JPanel(new GridLayout(2, 1));
            desno.setOpaque(false);
            desno.add(cena);
            desno.add(stanje);

            add(traka, BorderLayout.WEST);
            add(tekst, BorderLayout.CENTER);
            add(desno, BorderLayout.EAST);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Proizvod> list, Proizvod p, int index,
                boolean isSelected, boolean cellHasFocus) {
            setBackground(isSelected ? SELEKCIJA : Color.WHITE);

            // Polimorfizam: svaki tip proizvoda vraca svoju kategoriju, detalje i cenu
            traka.setBackground(bojaKategorije(p.getKategorija()));
            naziv.setText(p.getNaziv());
            detalji.setText(p.getKategorija() + "  |  " + p.getDetalji());
            detalji.setForeground(SIVA);

            double stara = p.getOsnovnaCena();
            double nova = p.izracunajCenu();
            if (nova < stara - 0.001) {
                cena.setText("<html><span style='color:#95A5A6'><s>" + String.format("%.2f", stara)
                        + "</s></span>&nbsp; <b>" + rsd(nova) + "</b></html>");
            } else {
                cena.setText("<html><b>" + rsd(nova) + "</b></html>");
            }

            int kolicina = p.getKolicina();
            if (kolicina == 0) {
                stanje.setText("Nema na stanju");
                stanje.setForeground(CRVENA);
                naziv.setForeground(SIVA);
            } else if (kolicina <= 3) {
                stanje.setText("Jo\u0161 samo " + kolicina);
                stanje.setForeground(NARANDZASTA);
                naziv.setForeground(TEKST);
            } else {
                stanje.setText("Na stanju: " + kolicina);
                stanje.setForeground(SIVA);
                naziv.setForeground(TEKST);
            }
            cena.setForeground(TEKST);
            return this;
        }
    }

    /** Red u listi korpe: naziv levo, cena desno. */
    private class KorpaRenderer extends JPanel implements ListCellRenderer<Proizvod> {
        private JLabel naziv = new JLabel();
        private JLabel cena = new JLabel();

        KorpaRenderer() {
            setLayout(new BorderLayout(10, 0));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, LINIJA),
                    BorderFactory.createEmptyBorder(10, 12, 10, 12)));
            naziv.setFont(new Font("SansSerif", Font.PLAIN, 14));
            naziv.setForeground(TEKST);
            cena.setFont(new Font("SansSerif", Font.BOLD, 14));
            cena.setForeground(TEKST);
            add(naziv, BorderLayout.CENTER);
            add(cena, BorderLayout.EAST);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Proizvod> list, Proizvod p, int index,
                boolean isSelected, boolean cellHasFocus) {
            setBackground(isSelected ? SELEKCIJA : Color.WHITE);
            naziv.setText(p.getNaziv());
            cena.setText(rsd(p.izracunajCenu()));
            return this;
        }
    }

    // ------------------------------------------------------------------
    // Akcije dugmadi
    // ------------------------------------------------------------------

    private void dodajUKorpu() {
        Proizvod izabran = ponudaLista.getSelectedValue();
        if (izabran == null) {
            prikaziGresku("Izaberite proizvod iz ponude.");
            return;
        }
        try {
            kupac.getKorpa().dodaj(izabran);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            prikaziGresku(ex.getMessage());
        }
        osveziPrikaz();
    }

    private void ukloniIzKorpe() {
        int indeks = korpaLista.getSelectedIndex();
        if (indeks == -1) {
            prikaziGresku("Izaberite stavku u korpi.");
            return;
        }
        try {
            kupac.getKorpa().ukloni(indeks);
        } catch (IllegalArgumentException ex) {
            prikaziGresku(ex.getMessage());
        }
        osveziPrikaz();
    }

    private void poruci() {
        try {
            Narudzbina n = kupac.naruci();
            JOptionPane.showMessageDialog(this, "Uspe\u0161no ste poru\u010dili!\n" + n, "Narud\u017ebina",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalStateException | IllegalArgumentException ex) {
            prikaziGresku(ex.getMessage());
        }
        osveziPrikaz();
    }

    private void primeniPopust() {
        String unos = JOptionPane.showInputDialog(this, "Popust u procentima (0-90):", "Popust",
                JOptionPane.QUESTION_MESSAGE);
        if (unos == null) {
            return; // Cancel
        }
        try {
            double procenat = Double.parseDouble(unos.trim().replace(',', '.'));
            int broj = prodavnica.primeniPopustNaSve(procenat);
            JOptionPane.showMessageDialog(this, "Popust je primenjen na " + broj
                    + " proizvoda (samo oni koji implementiraju Popust).", "Popust",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            prikaziGresku("Unesite broj, npr. 15.");
        } catch (IllegalArgumentException ex) {
            prikaziGresku(ex.getMessage());
        }
        osveziPrikaz();
    }

    private void prikaziNarudzbine() {
        ArrayList<Narudzbina> lista = kupac.getNarudzbine();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nemate narud\u017ebina.", "Narud\u017ebine",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Narudzbina n : lista) {
            sb.append(n).append("\n");
            for (Proizvod p : n.getProizvodi()) {
                sb.append("    - ").append(p.getNaziv()).append("\n");
            }
        }
        JTextArea tekst = new JTextArea(sb.toString(), 12, 40);
        tekst.setEditable(false);
        tekst.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JOptionPane.showMessageDialog(this, new JScrollPane(tekst), "Moje narud\u017ebine",
                JOptionPane.PLAIN_MESSAGE);
    }

    // ------------------------------------------------------------------
    // Osvezavanje
    // ------------------------------------------------------------------

    /** Ponovo puni obe liste iz modela (da se vide nove cene, stanje i korpa). */
    private void osveziPrikaz() {
        int izabranaPonuda = ponudaLista.getSelectedIndex();

        ponudaModel.clear();
        for (Proizvod p : prodavnica.getProizvodi()) {
            ponudaModel.addElement(p);
        }
        if (izabranaPonuda != -1) {
            ponudaLista.setSelectedIndex(izabranaPonuda);
        }

        korpaModel.clear();
        for (Proizvod p : kupac.getKorpa().getProizvodi()) {
            korpaModel.addElement(p);
        }

        korpaInfoLabel.setText("Stavki u korpi: " + korpaModel.getSize());
        ukupnoLabel.setText(rsd(kupac.getKorpa().ukupno()));
    }

    private void prikaziGresku(String poruka) {
        JOptionPane.showMessageDialog(this, poruka, "Gre\u0161ka", JOptionPane.ERROR_MESSAGE);
    }
}
