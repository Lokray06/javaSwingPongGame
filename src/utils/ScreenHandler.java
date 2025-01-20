package utils;

import DTOs.BallDTO;
import DTOs.BrickDTO;
import DTOs.ColorPaletteDTO;
import DTOs.PaletteDTO;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.List;

public class ScreenHandler extends JPanel {
    // Set up the colors
    public static ColorPaletteDTO currentColorPaletteDTO = ColorPalettes.colorPalettes.get(0);
    public static Color colorBall = Color.decode("#" + currentColorPaletteDTO.getColors()[3]);
    public static Color colorText = Color.decode("#" + currentColorPaletteDTO.getColors()[1]);
    public static Color colorBrick = Color.decode("#" + currentColorPaletteDTO.getColors()[2]);
    public static Color paletteColor = Color.decode("#" + currentColorPaletteDTO.getColors()[0]);
    public static Color backgroundColor = Color.decode("#" + currentColorPaletteDTO.getColors()[4]);
    
    public static boolean isFirstFrame = true;
    private static final long serialVersionUID = 1L;
    private List<PaletteDTO> palettes;
    private List<BallDTO> balls;
    private static Controller controller;
    
    public ScreenHandler(List<PaletteDTO> palettes, List<BallDTO> balls) {
        this.palettes = palettes;
        this.balls = balls;
        controller = new Controller(palettes, balls);
        setPreferredSize(new Dimension(GameWindow.screenX, GameWindow.screenY)); // Adjusted for new screen size
        setBackground(backgroundColor);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Enable antialiasing
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Draw GUI
        if(!Controller.gameOver) {
            // Draw all the rectangles based on the palettes list
            for (PaletteDTO palette : palettes) {
                g.setColor(new Color(palette.color)); // Set color
                g.fillRect(palette.posX, palette.posY, palette.width, palette.height); // Draw rectangle using width and height
            }
            
            // Draw all the balls
            for (BallDTO ball : balls) {
                g.setColor(new Color(ball.color)); // Set color
                g.fillOval(ball.posX, ball.posY, ball.radius, ball.radius); // Draw the ball
            }
            
            // Draw all the bricks
            if (!isFirstFrame) {
                for (BrickDTO brick : Controller.bricks) {
                    g.setColor(new Color(brick.color.getRGB())); // Set brick color
                    g.fillRect(brick.getPosX(), brick.getPosY(), brick.getWidth(), brick.getHeight()); // Draw brick
                }
            }
        }
    }
    
    public static void tick() {
        if (!Controller.gameOver || Controller.gameWon) {
            if (isFirstFrame) {
                controller.start();
            }
            controller.update(); // Call the controller to update positions
        }
        Toolkit.getDefaultToolkit().sync(); // Ensure smooth rendering
    }
}
