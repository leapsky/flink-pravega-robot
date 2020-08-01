package io.pravega.sharktank.flink.cozmo;

import javax.swing.*;
import java.awt.*;   // Using AWT's Graphics and Color
import java.net.URL;

/**
 * The class Sprite models a moving game object, with its own operations
 *  and can paint itself.
 */
public class Sprite {
    // Variables (package access)
    int x, y, width, height; // Use an rectangle for illustration
    String imgFilename;
    //Color color = Color.RED; // Color of the object

    // Images
    //private String imgCrossFilename = "images/cross.gif";
    //private String imgNoughtFilename = "images/nought.gif";
    //private Image imgCross;   // drawImage() uses an Image object
    //private Image imgNought;

    // Constructor
    public Sprite(int x, int y, int width, int height, String imgFilename) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imgFilename = imgFilename;
        //this.color = color;
    }

    // Paint itself given the Graphics context
    public void paint(Graphics g) {
        // Prepare the ImageIcon and Image objects for drawImage()
        ImageIcon iconCross = null;
        //ImageIcon iconNought = null;
        URL imgURL = getClass().getClassLoader().getResource(this.imgFilename);
        if (imgURL != null) {
            iconCross = new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + imgFilename);
        }
        Image img = iconCross.getImage();
        //Image img = Toolkit.getDefaultToolkit().createImage("/tmp/cozmo.gif");
        if (img == null) {
            System.out.println("sdfsloaded");
        }
        //g.setColor(color);
        //g.fillRect(x, y, width, height); // Fill a rectangle
        g.drawImage(img, x, y, this.width, this.height, null);
    }
}