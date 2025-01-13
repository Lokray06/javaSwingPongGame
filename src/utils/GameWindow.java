package utils;

import DTOs.BallDTO;
import DTOs.PaletteDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class GameWindow {
    private JFrame frame;
    private ScreenHandler screenHandler;
    private Controller controller;

    public static int screenX = 800;
    public static int screenY = 600;

    public GameWindow(List<PaletteDTO> palettes, List<BallDTO> balls) {

        // Initialize the game window
        frame = new JFrame("Pong Style Game");
        screenHandler = new ScreenHandler(palettes, balls);
        controller = new Controller(palettes, balls);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(screenHandler);
        frame.pack();
        frame.setResizable(false);
        frame.requestFocus();
        frame.setVisible(true);

        // Add key listener to listen for arrow key presses
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Check if left or right arrow keys are pressed
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    Controller.hInput = -1;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    Controller.hInput = 1;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    Controller.hInput = 0;
                }
            }
        });
        frame.setFocusable(true);  // Ensure that the window is focusable to receive key events

        // Timer to update the screen at regular intervals (60 FPS)
        Timer timer = new Timer(1000 / 60, e -> {
            ScreenHandler.updatePositions(); // Update the positions through Controller
            screenHandler.repaint(); // Repaint the screen
        });
        timer.start();
    }
}
