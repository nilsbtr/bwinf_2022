package _02verzinkt;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Aufgabe implements ActionListener {
    private JFrame window = new JFrame("Eine Generation");
    JButton[][] button = new JButton[100][100];

    Random random = new Random();

    boolean[][] zellen;
    boolean[][] neueZellen;
    private Farbe black;
    private Farbe gray;
    private Farbe dark;
    private Farbe light;
    private Farbe blue;

    public Aufgabe() {
        black = new Farbe(randomSpeed(), randomSpeed(), randomSpeed(), randomSpeed());
        gray = new Farbe(randomSpeed(), randomSpeed(), randomSpeed(), randomSpeed());
        light = new Farbe(randomSpeed(), randomSpeed(), randomSpeed(), randomSpeed());
        dark = new Farbe(randomSpeed(), randomSpeed(), randomSpeed(), randomSpeed());
        blue = new Farbe(randomSpeed(), randomSpeed(), randomSpeed(), randomSpeed());

        window.setSize(1000, 1000);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new GridLayout(button.length, button[0].length));

        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button[i].length; j++) {
                button[i][j] = new JButton("");
                window.add(button[i][j]);
                button[i][j].setBackground(Color.WHITE);
                button[i][j].addActionListener(this);
            }
        }

        window.setVisible(true);
    }

    public static void main(String[] args) {
        Aufgabe g = new Aufgabe();
    }

    public void actionPerformed(ActionEvent e) {
        for (int y = 1; y < button.length - 1; y++) {
            for (int x = 1; x < button[y].length - 1; x++) {
                if (e.getSource() == button[y][x]) {
                    button[y][x].setBackground(randomColor());
                }
            }
        }
    }

    public void generationenErstellen() {
        zellen = new boolean[button[0].length][button.length];
        neueZellen = new boolean[button[0].length][button.length];
        felderScannen();
        boolean p = true;

        do {
            p = false;
            for (int y = 1; y < zellen.length - 1; y++) {
                for (int x = 1; x < zellen[y].length - 1; x++) {
                    neueZellen[y][x] = pruefeNachbarn(x, y);
                }
            }

            for (int y = 1; y < zellen.length - 1; y++) {
                for (int x = 1; x < zellen[y].length - 1; x++) {
                    zellen[y][x] = neueZellen[y][x];

                    if (zellen[y][x]) {
                        button[y][x].setBackground(Color.BLACK);
                    } else {
                        button[y][x].setBackground(Color.WHITE);
                    }
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Ist das Feld leer?
            for (int y = 0; y < zellen.length; y++) {
                for (int x = 0; x < zellen[y].length; x++) {
                    if (zellen[y][x]) {
                        p = true;
                    }
                }
            }
        } while (p);
    }

    private void felderScannen() {
        for (int y = 0; y < button.length; y++) {
            for (int x = 0; x < button[y].length; x++) {
                Color c = button[y][x].getBackground();

                if (c != Color.WHITE) {
                    if (c == Color.BLACK) {
                        crystalGrowth(black, x, y);
                    } else if (c == Color.DARK_GRAY) {
                        crystalGrowth(dark, x, y);
                    } else if (c == Color.GRAY) {
                        crystalGrowth(gray, x, y);
                    } else if (c == Color.LIGHT_GRAY) {
                        crystalGrowth(light, x, y);
                    } else if (c == Color.BLUE) {
                        crystalGrowth(blue, x, y);
                    }
                }
            }
        }
    }

    private void crystalGrowth(Farbe f, int x, int y) {

    }

    private Color randomColor() {
        Color[] c = { Color.BLACK, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, Color.BLUE };
        return c[random.nextInt(c.length)];
    }

    private int randomSpeed() {
        return (int) (Math.random() * (3 - 1)) + 1;
    }

    private boolean pruefeNachbarn(int x, int y) {
        // n = Nachbarzellen
        // Bei mehr als 3 Nachbarn stirbt die Zelle
        int n = zaehleNachbarn(x, y);
        if (zellen[y][x]) {
            n = n - 1;
            return (n == 2) || (n == 3);
        } else {
            return (n == 3);
        }
    }

    private int zaehleNachbarn(int x, int y) {
        /*
         * X X X
         * X O X
         * X X X
         */
        int count = 0;
        // [x-1][y-1] oben links - [x+1][y+1] unten rechts
        for (int i = y - 1; i <= y + 1; i++) {
            for (int j = x - 1; j <= x + 1; j++) {
                if (zellen[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
}