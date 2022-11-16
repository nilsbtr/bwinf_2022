package _02verzinkt;

import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;

import org.apache.commons.io.FileUtils;

public class Aufgabe {
    Pixel[][] pixels;
    Point[] marker;
    int roots;
    int width;
    int height;
    Random random;

    public Aufgabe(int roots, int height, int width) {
        try {
            FileUtils.cleanDirectory(new File("pic/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.roots = roots;
        this.height = height;
        this.width = width;
        random = new Random();
    }

    public void createGeneration(boolean pictures) {
        pixels = new Pixel[height][width];
        placeRoots(roots);

        int c = 0;
        boolean incomplete = false;

        do {
            incomplete = false;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
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
                    createPicture("pic/picture" + c + ".pgm");
                } else {
                    System.out.println(c);
                }

            }
            c++;
        } while (incomplete);

        markRoots();
        createPicture("pic/picturefinal.pgm");
        System.out.println("DONE! Checked the field " + c + " times.");
    }

    private void placeRoots(int roots) {
        marker = new Point[roots];
        int i = 0;

        while (i < roots) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);

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
        if ((x >= 0 && x < width) && (y >= 0 && y < height)) {
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
            out.println(width + " " + height);
            out.println("255");

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
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
        Aufgabe a = new Aufgabe(300, 720, 1280);
        a.createGeneration(false);
    }
}