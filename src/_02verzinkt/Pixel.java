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
        color = r.nextInt(175 - 75) + 50;
        sU = r.nextInt(80 - 10) + 10;
        sD = r.nextInt(80 - 10) + 10;
        sR = r.nextInt(80 - 10) + 10;
        sL = r.nextInt(80 - 10) + 10;
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

    // Speed

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

    // Cooldowns

    public boolean cooldownUp() {
        if (cU == 0) {
            cU = sU;
            return true;
        } else {
            return false;
        }
    }

    public boolean cooldownDown() {
        if (cD == 0) {
            cD = sD;
            return true;
        } else {
            return false;
        }
    }

    public boolean cooldownRight() {
        if (cR == 0) {
            cR = sR;
            return true;
        } else {
            return false;
        }
    }

    public boolean cooldownLeft() {
        if (cL == 0) {
            cL = sL;
            return true;
        } else {
            return false;
        }
    }

    public void nextStep() {
        cU = cU - 1;
        cD = cD - 1;
        cR = cR - 1;
        cL = cL - 1;
    }
}
