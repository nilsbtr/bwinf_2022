package _02verzinkt;

import java.util.Random;

public class Pixel {
    private int color;

    // Saved Cooldowns (Speed, higher = slower)
    private int sU;
    private int sD;
    private int sR;
    private int sL;

    // Current Cooldowns
    private int cU;
    private int cD;
    private int cR;
    private int cL;

    // Create a pixel (random speeds in given borders)

    public Pixel() {
        Random r = new Random();
        color = r.nextInt(Values.COLOR_MAX - Values.COLOR_MIN) + Values.COLOR_MIN;
        sU = r.nextInt(Values.COOLDOWN_UP_MAX - Values.COOLDOWN_UP_MIN) + Values.COOLDOWN_UP_MIN;
        sD = r.nextInt(Values.COOLDOWN_DOWN_MAX - Values.COOLDOWN_DOWN_MIN) + Values.COOLDOWN_DOWN_MIN;
        sR = r.nextInt(Values.COOLDOWN_RIGHT_MAX - Values.COOLDOWN_RIGHT_MIN) + Values.COOLDOWN_RIGHT_MIN;
        sL = r.nextInt(Values.COOLDOWN_LEFT_MAX - Values.COOLDOWN_LEFT_MIN) + Values.COOLDOWN_LEFT_MIN;
        cU = sU;
        cD = sD;
        cR = sR;
        cL = sL;
    }

    // Create a copy of another pixel

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

    // Color Getter and Setter

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    // Speed Getter

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

    // Cooldowns check and count

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
