package _02verzinkt;

import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Main {
    Pixel[][] pixels;
    Point[] marker;
    String path;
    Random random;

    public Main() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hhmmss");
        path = "pic/" + dateFormat.format(now);
        File dir = new File(path);
        dir.mkdir();
        random = new Random();
    }

    public void createGeneration(boolean pictures) {
        pixels = new Pixel[Values.HEIGHT][Values.WIDTH];
        placeRoots();

        int c = 0;
        boolean incomplete = false;

        do {
            incomplete = false;
            for (int y = 0; y < Values.HEIGHT; y++) {
                for (int x = 0; x < Values.WIDTH; x++) {
                    if (pixels[y][x] != null) {
                        checkPixel(pixels[y][x], x, y);
                    } else if (!incomplete) {
                        incomplete = true;
                    }
                }
            }
            // Jede Bilderstellung benötigt ca. 7sek bei einer Auflösung von 720p!!!
            if ((c < 1000 && c % 100 == 0) || c % 1000 == 0) {
                if (pictures) {
                    createPicture(path + "/picture" + c + ".pgm");
                } else {
                    System.out.println("Schritt: " + c);
                }

            }
            c++;
        } while (incomplete);

        markRoots();
        createPicture(path + "/picturefinal.pgm");
        System.out.println("DONE! Checked the field " + c + " times.");
    }

    private void placeRoots() {
        marker = new Point[Values.ROOTS];
        int i = 0;

        while (i < Values.ROOTS) {
            int x = random.nextInt(Values.WIDTH - 1);
            int y = random.nextInt(Values.HEIGHT - 1);

            if (pixels[y][x] == null) {
                marker[i] = new Point(x, y);
                pixels[y][x] = new Pixel();
                i++;
            }
        }
    }

    private void checkPixel(Pixel p, int x, int y) {
        if (p.cooldownUp()) {
            createPixel(p, x, y + 1);
        }
        if (p.cooldownDown()) {
            createPixel(p, x, y - 1);
        }
        if (p.cooldownRight()) {
            createPixel(p, x + 1, y);
        }
        if (p.cooldownLeft()) {
            createPixel(p, x - 1, y);
        }
        p.nextStep();
    }

    private void createPixel(Pixel p, int x, int y) {
        if ((x >= 0 && x < Values.WIDTH) && (y >= 0 && y < Values.HEIGHT)) {
            if (pixels[y][x] == null) {
                pixels[y][x] = new Pixel(p.getColor(), p.getUp(), p.getDown(), p.getRight(), p.getLeft());
            }
        }
    }

    private void markRoots() {
        for (int i = 0; i < marker.length; i++) {
            pixels[(int) marker[i].getY()][(int) marker[i].getX()].setColor(0);
        }
    }

    private void createPicture(String filename) {
        try (PrintStream out = new PrintStream(new FileOutputStream(filename))) {
            out.println("P2");
            out.println(Values.WIDTH + " " + Values.HEIGHT);
            out.println("255");

            for (int y = 0; y < Values.HEIGHT; y++) {
                for (int x = 0; x < Values.WIDTH; x++) {
                    if (pixels[y][x] == null) {
                        out.print("255");
                    } else {
                        out.print(pixels[y][x].getColor());
                    }
                    out.print(" ");
                }
                out.println();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("Created file " + filename);
    }

    public static void main(String[] args) {
        Main a = new Main();
        a.createGeneration(false);
    }
}