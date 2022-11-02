package _02verzinkt;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;

public class Main {
    Pixel[][] pixels;
    Queue<int[]> rpos = new Queue<>();

    public Main(int roots) {
        pixels = new Pixel[720][1280];
        placeRoots(roots);
        createGeneration();
    }

    public void createGeneration() {
        int c = 0;
        do {
            for (int y = 0; y < pixels.length; y++) {
                for (int x = 0; x < pixels[y].length; x++) {
                    if (pixels[y][x] != null) {
                        checkPixel(pixels[y][x], x, y);
                    }
                }
            }
            c++;
            if (c == 5 || c == 20 || c == 50 || c == 100 || c == 200 || c == 350 || c % 500 == 0) {
                createPicture("picture" + c + ".pgm");
            }
        } while (emptyField());

        System.out.println("Checked the field " + c + " times.");

        createPicture("picturefinal.pgm");
    }

    private void placeRoots(int roots) {
        Random r = new Random();
        for (int i = 0; i < roots; i++) {
            int x = r.nextInt(pixels[0].length - 1);
            int y = r.nextInt(pixels.length - 1);

            if (pixels[y][x] == null) {
                int[] tmp = { x, y };
                rpos.enqueue(tmp);
                pixels[y][x] = new Pixel();
            } else {
                roots++;
            }
        }
    }

    private void checkPixel(Pixel p, int x, int y) {
        if (p.cooldownUp() == 0) {
            createPixel(p, x, y + 1);
            p.resetUp();
        }
        if (p.cooldownDown() == 0) {
            createPixel(p, x, y - 1);
            p.resetDown();
        }
        if (p.cooldownRight() == 0) {
            createPixel(p, x + 1, y);
            p.resetRight();
        }
        if (p.cooldownLeft() == 0) {
            createPixel(p, x - 1, y);
            p.resetLeft();
        }
        p.nextStep();
    }

    private void createPixel(Pixel p, int x, int y) {
        if ((x >= 0 && x < pixels[0].length) && (y >= 0 && y < pixels.length)) {
            if (pixels[y][x] == null) {
                pixels[y][x] = new Pixel(p.getColor(), p.getUp(), p.getDown(), p.getRight(), p.getLeft());
            }
        }
    }

    private boolean emptyField() {
        for (int y = 0; y < pixels.length; y++) {
            for (int x = 0; x < pixels[y].length; x++) {
                if (pixels[y][x] == null) {
                    return true;
                }
            }
        }
        return false;
    }

    private void createPicture(String filename) {
        try (PrintStream out = new PrintStream(new FileOutputStream(filename))) {
            out.println("P2");
            out.println(pixels[0].length + " " + pixels.length);
            out.println("255");

            for (int y = 0; y < pixels.length; y++) {
                for (int x = 0; x < pixels[y].length; x++) {
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
        Main m = new Main(200);
    }
}