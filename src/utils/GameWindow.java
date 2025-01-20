package utils;

import DTOs.PaletteDTO;
import DTOs.BallDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Time;
import java.util.List;
import java.util.concurrent.*;

public class GameWindow {
    private JFrame frame;
    private ScreenHandler screenHandler;
    private Controller controller;
    private JPanel overlayPanel;
    
    public static int screenX = 800;
    public static int screenY = 600;  // Corrected to 600 as per your original requirement
    public static int marginX = 60;
    public static int marginY = 60;
    
    private static final int FPS = 60;
    private static final long INTERVAL = 1000L / FPS;
    
    public GameWindow(List<PaletteDTO> palettes, List<BallDTO> balls) {
        
        // Initialize the game window
        frame = new JFrame("Pong Style Game");
        screenHandler = new ScreenHandler(palettes, balls);
        controller = new Controller(palettes, balls);
        
        // Set the window size to fit both the game and the UI overlay (add 40px for the UI overlay)
        frame.setSize(screenX + 16, screenY + 79);  // Correct the window height to accommodate the overlay
        frame.setLocationRelativeTo(null);  // Center the window on the screen
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  // Prevent the default closing behavior
        
        // Add WindowListener to handle window closing
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Set gameRunning to false when the window is closed
                GameModeSelectionWindow.gameRunning = false;
                frame.dispose();  // Close the window
            }
        });
        
        // Create a JLayeredPane for layered components
        JLayeredPane layeredPane = new JLayeredPane();
        frame.add(layeredPane);
        
        // Set layout to null to manually position the components
        layeredPane.setLayout(null);
        
        // Set the bounds of the game panel (ScreenHandler), with no change to its original size
        screenHandler.setBounds(0, 40, screenX, screenY);  // Shifting 40px down to leave room for overlay
        
        // Create a new overlay panel (for score, lives, time)
        overlayPanel = new JPanel() {
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                setBackground(ScreenHandler.paletteColor);
                
                // Enable antialiasing for smoother text rendering
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // Draw score, lives, and time
                g2d.setColor(ScreenHandler.backgroundColor);
                g2d.setFont(new Font("Monospace", Font.BOLD, 20));
                
                if (!Controller.gameOver) {
                    // Draw Score
                    g2d.drawString("Score: " + Controller.score, 10, 26);
                    
                    // Draw Lives
                    g2d.drawString("Lives: " + Controller.lives, 360, 26);
                    
                    // Draw Time
                    g2d.drawString("Time: " + Controller.gameTime, 600, 26);
                }
                
                Time gameTime = new Time(Controller.elapsedTimeSeconds * 1000L);
                gameTime.setHours(0);
                
                // Draw game over or game won message
                if (Controller.gameOver) {
                    
                    overlayPanel.setBackground(ScreenHandler.backgroundColor);
                    overlayPanel.setBounds(0, 0, 800, 600);
                    g2d.setColor(ScreenHandler.colorBall);
                    g2d.drawString("GAME OVER", 370, 310);
                    g2d.drawString("Final score: " + Controller.score, 360, 350);
                    g2d.drawString("Time: " + gameTime, 360, 390);
                }
                if (Controller.gameWon) {
                    overlayPanel.setBackground(ScreenHandler.backgroundColor);
                    overlayPanel.setBounds(0, 0, 800, 600);
                    g2d.setColor(ScreenHandler.colorBall);
                    g2d.drawString("YOU WON", 370, 310);
                    g2d.drawString("Final score: " + Controller.score, 360, 350);
                    g2d.drawString("Time: " + gameTime, 360, 390);
                }
            }
        };
        overlayPanel.setOpaque(true); // Make the panel transparent to show the game behind
        
        overlayPanel.setBounds(0, 0, screenX + marginX, 40);  // Set the overlay panel's bounds (it stays at the top)
        
        // Add the components to the layered pane
        layeredPane.add(screenHandler, Integer.valueOf(0));  // Game panel at layer 0 (background)
        layeredPane.add(overlayPanel, Integer.valueOf(1));    // Overlay panel at layer 1 (on top)
        
        // Pack and display the window
        frame.setResizable(true);
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
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    Controller.hInput = 0;
                }
            }
        });
        frame.setFocusable(true);  // Ensure that the window is focusable to receive key events
        
        // Set up a ScheduledExecutorService to update the screen at regular intervals
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        
        if (!Controller.gameOver || !Controller.gameWon) {
            executor.scheduleAtFixedRate(() -> {
                ScreenHandler.tick(); // Update game logic
                screenHandler.repaint(); // Repaint the screen
                overlayPanel.repaint(); // Repaint the overlay panel
            }, 0, INTERVAL, TimeUnit.MILLISECONDS);
        }
    }
}
