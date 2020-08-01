package io.pravega.sharktank.flink.cozmo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MoveCozmo extends JFrame {
    // Define constants for the various dimensions
    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 600;
    public static final int INIT_SPRITE_X = 50;
    public static final int INIT_SPRITE_Y = 36;

    private boolean PUPPY_FOUND = false;
    private boolean MINION_FOUND = false;
    private boolean TOTO_FOUND = false;
    private DrawCanvas canvas; // the custom drawing canvas (an inner class extends JPanel)
    private Sprite sprite;     // the moving object
    private Container contentPane;

    // Constructor to set up the GUI components and event handlers
    public MoveCozmo() {

        // Construct a sprite given x, y, width, height, image
        this.sprite = new Sprite(INIT_SPRITE_X, INIT_SPRITE_Y, 50, 50, "images/cozmo.png");

        // Set up the custom drawing canvas (JPanel)
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Add both panels to this JFrame
        contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(canvas, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cozmo Maze");
        pack();            // pack all the components in the JFrame
        setVisible(true);  // show it
        requestFocus();    // "super" JFrame requests focus to receive KeyEvent
    }

    public void showFoundObjects() {

        // Set up the custom drawing canvas (JPanel)
        DrawCanvas2 canvas2 = new DrawCanvas2();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        contentPane.removeAll();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(canvas2);
        contentPane.revalidate();
        contentPane.repaint();
    }

    // Helper method to move the sprite left
    public void goTo(int x, int y) {
        // update sprite
        // Repaint only the affected areas, not the entire JFrame, for efficiency
        canvas.repaint(sprite.x, sprite.y, sprite.width, sprite.height); // Clear old area to background
        sprite.x = x;
        sprite.y = y;
        canvas.repaint(sprite.x, sprite.y, sprite.width, sprite.height); // Paint new location
    }

    public void slowMove(int x, int y) {
        x = x + INIT_SPRITE_X;
        y = y + INIT_SPRITE_Y;
        int newX = sprite.x;
        int newY = sprite.y;
        while ((sprite.x != x) || (sprite.y != y)) {
            if (sprite.x < x) {
                newX = sprite.x + 1;
            }
            if (sprite.y < y) {
                newY = sprite.y + 1;
            }
            if (sprite.x > x) {
                newX = sprite.x - 1;
            }
            if (sprite.y > y) {
                newY = sprite.y - 1;
            }
            try {
                TimeUnit.MICROSECONDS.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            goTo(newX, newY);
        }
    }

    public boolean findObject(String fileName) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet("http://127.0.0.1:8080/recognizeImage?image=" + fileName);
        double maxMatchValue = 0.8;
        String matchObject = "";

        try {
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (InputStream stream = entity.getContent()) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(stream));
                    StringBuilder sb = new StringBuilder();
                    for (int c; (c = in.read()) >= 0;)
                        sb.append((char)c);
                    String rsp = sb.toString();

                    JSONParser parser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) parser.parse(rsp);
                    System.out.println(jsonObject);
                    double duration = (double) jsonObject.get("seconds");
                    System.out.println("Duration: " + duration + " seconds");
                    System.out.println(jsonObject.get("answer"));
                    JSONObject jsonObjectAnswer = (JSONObject) jsonObject.get("answer");

                    for (Object id : jsonObjectAnswer.keySet()) {
                        double val = (double) jsonObjectAnswer.get(id);
                        System.out.println(id + " : " + val);
                        if (val > maxMatchValue) {
                            maxMatchValue = val;
                            matchObject = id.toString();
                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } catch (ParseException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (matchObject.equals("puppy")) {
            PUPPY_FOUND = true;
            return true;
        } else if (matchObject.equals("minion")) {
            MINION_FOUND = true;
            return true;
        } else  if (matchObject.equals("toto")) {
            TOTO_FOUND = true;
            return true;
        }
        return false;
    }

    // Define inner class DrawCanvas, which is a JPanel used for custom drawing
    class DrawCanvas extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
/////
            String imgBackgroundFalename = "images/maze.jpg";
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
            g.drawImage(imgBackground, 0, 0, 490, 600, null);

/////
            sprite.paint(g);  // the sprite paints itself
        }
    }

    // Define inner class DrawCanvas2, which is a JPanel used for custom drawing
    class DrawCanvas2 extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);
/////
            String imgBackgroundFalename = "images/maze.jpg";
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
            g.drawImage(imgBackground, 0, 0, 490, 600, null);
            Font font = new Font("Verdana", Font.BOLD, 20);
            g.setFont(font);
            g.drawString("Found objects", 550, 30);
/////
            sprite.paint(g);  // the sprite paints itself

            if (PUPPY_FOUND) {
                Sprite sprite2 = new Sprite(550, 0, 150, 200, "images/puppy.png");
                sprite2.paint(g);
            }
            if (MINION_FOUND) {
                Sprite sprite3 = new Sprite(550, 200, 150, 200, "images/minion.png");
                sprite3.paint(g);
            }
            if (TOTO_FOUND) {
                Sprite sprite4 = new Sprite(550, 400, 150, 200, "images/toto.png");
                sprite4.paint(g);
            }
        }
    }

    // The entry main() method
    public static void main(String[] args) {
        MoveCozmo moveCozmo = new MoveCozmo(); // Let the constructor do the job
        moveCozmo.goTo(100, 100);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        moveCozmo.slowMove(50, 100);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        moveCozmo.slowMove(100, 100);
        moveCozmo.slowMove(100, 200);
        moveCozmo.slowMove(100, 100);
        //moveCozmo.findObject("fromcozmo-91.jpeg");

        moveCozmo.showFoundObjects();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        moveCozmo.showFoundObjects();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
        }

        moveCozmo.showFoundObjects();
    }
}