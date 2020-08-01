package io.pravega.sharktank.flink.cozmo;

import java.awt.*;       // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT's event classes and listener interfaces
import javax.swing.*;    // Using Swing's components and containers
import java.net.URL;
import java.util.concurrent.TimeUnit;
/**
 * Custom Graphics Example: Using key/button to move a object left or right.
 * The moving object (sprite) is defined in its own class, with its own
 * operations and can paint itself.
 */
public class MoveASprite extends JFrame {
    // Define constants for the various dimensions
    public static final int CANVAS_WIDTH = 400;
    public static final int CANVAS_HEIGHT = 400;
    public static final Color CANVAS_BG_COLOR = Color.CYAN;

    private DrawCanvas canvas; // the custom drawing canvas (an inner class extends JPanel)
    private Sprite sprite;     // the moving object

    // Constructor to set up the GUI components and event handlers
    public MoveASprite() {

        // Construct a sprite given x, y, width, height, color
        sprite = new Sprite(CANVAS_WIDTH / 2 - 5, CANVAS_HEIGHT / 2 - 40,
                50, 50, "images/cross.gif");

        // Set up a panel for the buttons
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnLeft = new JButton("Move Left ");
        btnPanel.add(btnLeft);
        btnLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                moveLeft();
                requestFocus(); // change the focus to JFrame to receive KeyEvent
            }
        });
        JButton btnRight = new JButton("Move Right");
        btnPanel.add(btnRight);
        btnRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                moveRight();
                requestFocus(); // change the focus to JFrame to receive KeyEvent
            }
        });
        JButton btnUp = new JButton("Move Un");
        btnPanel.add(btnUp);
        btnUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                moveUp();
                requestFocus(); // change the focus to JFrame to receive KeyEvent
            }
        });

        // Set up the custom drawing canvas (JPanel)
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Add both panels to this JFrame
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);
        cp.add(btnPanel, BorderLayout.SOUTH);

        // "super" JFrame fires KeyEvent
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt) {
                switch(evt.getKeyCode()) {
                    case KeyEvent.VK_LEFT:  moveLeft();  break;
                    case KeyEvent.VK_RIGHT: moveRight(); break;
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Move a Sprite");
        pack();            // pack all the components in the JFrame
        setVisible(true);  // show it
        requestFocus();    // "super" JFrame requests focus to receive KeyEvent
    }

    // Helper method to move the sprite left
    private void moveLeft() {
        // Save the current dimensions for repaint to erase the sprite
        int savedX = sprite.x;
        // update sprite
        sprite.x -= 10;
        // Repaint only the affected areas, not the entire JFrame, for efficiency
        canvas.repaint(savedX, sprite.y, sprite.width, sprite.height); // Clear old area to background
        canvas.repaint(sprite.x, sprite.y, sprite.width, sprite.height); // Paint new location
    }

    // Helper method to move the sprite right
    private void moveRight() {
        // Save the current dimensions for repaint to erase the sprite
        int savedX = sprite.x;
        // update sprite
        sprite.x += 10;
        // Repaint only the affected areas, not the entire JFrame, for efficiency
        canvas.repaint(savedX, sprite.y, sprite.width, sprite.height); // Clear old area to background
        canvas.repaint(sprite.x, sprite.y, sprite.width, sprite.height); // Paint at new location
    }

    // Helper method to move the sprite right
    private void moveUp() {
        // Save the current dimensions for repaint to erase the sprite
        int savedY = sprite.y;
        // update sprite
        sprite.y -= 10;
        // Repaint only the affected areas, not the entire JFrame, for efficiency
        canvas.repaint(sprite.x, savedY, sprite.width, sprite.height); // Clear old area to background
        canvas.repaint(sprite.x, sprite.y, sprite.width, sprite.height); // Paint at new location
    }

    // Define inner class DrawCanvas, which is a JPanel used for custom drawing
    class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
/////
            String imgBackgroundFalename = "images/java.png";
            Image imgBackground;   // drawImage() uses an Image object

            ImageIcon iconBackground = null;
            //ImageIcon iconNought = null;
            URL imgURL = getClass().getClassLoader().getResource(imgBackgroundFalename);
            if (imgURL != null) {
                iconBackground = new ImageIcon(imgURL);
            } else {
                System.err.println("Couldn't find file: " + imgBackgroundFalename);
            }
            imgBackground = iconBackground.getImage();
            //g.setColor(color);
            //g.fillRect(x, y, width, height); // Fill a rectangle
            g.drawImage(imgBackground, 0, 0, 350, 350, null);

/////
            sprite.paint(g);  // the sprite paints itself
        }
    }

    // The entry main() method
    public static void main(String[] args) {
        // Run GUI construction on the Event-Dispatching Thread for thread safety
        //SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
                MoveASprite masprite = new MoveASprite(); // Let the constructor do the job
                masprite.moveLeft();
                //masprite.requestFocus();

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted");
                }

                masprite.moveLeft();
                //masprite.requestFocus();
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted");
                }

                masprite.moveUp();
//            }
//        });
    }
}