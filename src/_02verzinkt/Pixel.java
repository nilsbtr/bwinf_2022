package _02verzinkt;

import java.util.Random;

public class Pixel {
    private int color;

    // Speeds
    private int sU;
    private int sD;
    private int sR;
    private int sL;

    // Cooldowns
    private int cU;
    private int cD;
    private int cR;
    private int cL;

    public Pixel() {
        Random r = new Random();
        color = r.nextInt(205 - 50) + 50;
        sU = r.nextInt(100 - 1) + 1;
        sD = r.nextInt(100 - 1) + 1;
        sR = r.nextInt(100 - 1) + 1;
        sL = r.nextInt(100 - 1) + 1;
        cU = sU;
        cD = sD;
        cR = sR;
        cL = sL;
    }

    public Pixel(int color, int up, int down, int right, int left) {
        this.color = color;
        sU = up;
        sD = down;
        sR = right;
        sL = left;
        cU = up;
        cD = down;
        cR = right;
        cL = left;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    // Getters Speed

    public int getUp() {
        return sU;
    }

    public int getDown() {
        return sD;
    }

    public int getRight() {
        return sR;
    }

    public int getLeft() {
        return sL;
    }

    // Getters Cooldowns

    public int cooldownUp() {
        return cU;
    }

    public int cooldownDown() {
        return cD;
    }

    public int cooldownRight() {
        return cR;
    }

    public int cooldownLeft() {
        return cL;
    }

    public void nextStep() {
        cU = cU - 1;
        cD = cD - 1;
        cR = cR - 1;
        cL = cL - 1;
    }

    // Cooldown Resets

    public void resetUp() {
        cU = sU;
    }

    public void resetDown() {
        cD = sD;
    }

    public void resetRight() {
        cR = sR;
    }

    public void resetLeft() {
        cL = sL;
    }
}
