package main;

import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import inputs.KeyboardListener;
import inputs.MyMouseListener;

public class App extends JFrame implements Runnable {

    private AppScreen appScreen;

    private BufferedImage img;

    private Thread gameThread;

    private final double FPS_SET = 120.0;
    private final double UPS_SET = 60.0;

    private MyMouseListener myMouseListener;
    private KeyboardListener keyboardListener;

    public App() {

        setDefaultCloseOperation(EXIT_ON_CLOSE); // Cuando se cierra la ventana se finaliza el programa

        appScreen = new AppScreen(this);
        add(appScreen);

        pack();
        setLocationRelativeTo(null); // Centra la ventana
        setVisible(true);
    }

    private void initInputs() {
        myMouseListener = new MyMouseListener();
        keyboardListener = new KeyboardListener();

        addMouseListener(myMouseListener);
        addMouseMotionListener(myMouseListener);
        addKeyListener(keyboardListener);

        requestFocus();
    }

    private void start() {
        gameThread = new Thread(this) {
        };

        gameThread.start();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Inició el juego!");

        App app = new App();

        app.initInputs();
        app.start();
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET; // Un segundo (en nanosegundos) para tener 60 fps
        double timePerUpdate = 1000000000.0 / UPS_SET; // Un segundo (en nanosegundos) para tener 60 ups

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        long now;

        while (true) {

            now = System.nanoTime();
            // Render
            if (now - lastFrame >= timePerFrame) {
                repaint();
                lastFrame = now;
                frames++;
            }

            // Update
            if (now - lastUpdate >= timePerUpdate) {
                // updateGame();
                lastUpdate = now;
                updates++;
            }

            // Checking FPS y UPS
            if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }

    }
}
