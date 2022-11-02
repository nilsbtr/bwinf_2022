
/*
 * 				www.ulasdikme.com
 * This code makes you can take and redraw
 * black points on the image
 * */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {

    public static String image = "3.png";

    public static BufferedImage readImage(String fileLocation) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static void Window() {

        TakeBlackPointsFromImage example = new TakeBlackPointsFromImage();

        BufferedImage image1 = readImage("bin/" + image);
        int width = image1.getWidth();
        int height = image1.getHeight() + 30;

        JFrame frame = new JFrame("ReDraw");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(example, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setSize(width, height);
    }

    public static void init() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Window();
            }
        });
    }

} // end of class Window

class TakeBlackPointsFromImage extends JPanel {

    public int t = 0;

    public static String image = Window.image;

    private static final long serialVersionUID = 1L;
    int b = 0;

    public static BufferedImage readImage(String fileLocation) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(fileLocation));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    public static int image() {

        BufferedImage image1 = readImage("bin/" + image);
        int width = image1.getWidth();

        return width;
    }

    public static int takeNumberOfDesiredPixels() {
        BufferedImage image22 = readImage("bin/" + image);

        int t = 0; // how many desired points existed ?
        int width = image22.getWidth();
        int height = image22.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                int pixel = image22.getRGB(i, j);
                int red = (pixel >> 16) & 0x000000FF;
                int green = (pixel >> 8) & 0x000000FF;
                int blue = (pixel) & 0x000000FF;

                if (red == 0 && green == 0 && blue == 0) { // for the take Black
                                                           // pixels
                    // Black pixel has RGB value is zero
                    t++;

                }
            }
        } // end of the outer for loop

        return t;

    }

    /** Take x coordinates of desired pixels **/
    public static int[] getXCoordinates() {
        BufferedImage image22 = readImage("bin/" + image);

        int width = image22.getWidth();
        int height = image22.getHeight();

        int[] ArrayX = new int[takeNumberOfDesiredPixels()];

        int d = -1;

        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {

                int pixel = image22.getRGB(w, h);

                int red = (pixel >> 16) & 0x000000FF;
                /**
                 * Shift all bits to the rigt as 16 bit
                 * then multiply or sth like filter with
                 * 0x000000FF then get the only last 8 bit
                 * which is the red information
                 * between 0 and 255
                 **/
                int green = (pixel >> 8) & 0x000000FF;
                /**
                 * Shift all bits to the rigt as 8 bit
                 * then multiply or sth like filter with
                 * 0x000000FF then get the only last 8 bit
                 * which is the green information
                 * between 0 and 255
                 **/

                int blue = (pixel) & 0x000000FF;
                /**
                 * multiply or sth like filter with
                 * 0x000000FF then get the only last 8 bit
                 * which is the blue information
                 * between 0 and 255
                 **/

                if (red == 0 && green == 0 && blue == 0) {
                    // for the take Black pixels
                    // Black pixel has RGB value is zero

                    d++;
                    ArrayX[d] = w;

                }
            }
        } // end of the outer for loop

        return ArrayX;

    }

    /** Take y coordinates of desired pixels **/
    public static int[] getYCoordinates() {
        BufferedImage image22 = readImage("bin/" + image);

        int width = image22.getWidth();
        int height = image22.getHeight();

        int[] ArrayY = new int[takeNumberOfDesiredPixels()];

        int d = -1;
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {

                int pixel = image22.getRGB(w, h);
                int red = (pixel >> 16) & 0x000000FF;
                int green = (pixel >> 8) & 0x000000FF;
                int blue = (pixel) & 0x000000FF;

                if (red == 0 && green == 0 && blue == 0) { // for the take Black
                                                           // pixels
                    // Black pixel has RGB value is zero

                    d++;

                    ArrayY[d] = h;

                }
            }
        } // end of the outer for loop

        return ArrayY;

    }

    @Override
    protected void paintComponent(Graphics g) {

        int c[] = getXCoordinates();
        int a = c.length;

        int d[] = getYCoordinates();

        g.setColor(Color.black);
        for (int y = 0; y < a; y++) {

            g.drawLine(c[y], d[y], c[y], d[y]);
        }

    }
}// end of the class cizgi