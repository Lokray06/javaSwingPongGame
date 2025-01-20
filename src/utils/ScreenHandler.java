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
    //Set up the colors
    public static ColorPaletteDTO currentColorPaletteDTO = ColorPalettes.colorPalettes.get(0);
    public static Color colorBall = Color.decode("#" + currentColorPaletteDTO.getColors()[3]);
    public static Color colorText = Color.decode("#" + currentColorPaletteDTO.getColors()[1]);
    public static Color colorBrick =Color.decode("#" + currentColorPaletteDTO.getColors()[2]);
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
        setPreferredSize(new Dimension(800, 600)); // Set screen size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Enable antialiasing
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Draw GUI
        g2d.setColor(colorText);
        g2d.setFont(new Font("Monospace", Font.BOLD, 20));

        if(!Controller.gameOver)
        {


            // Points
            g2d.drawString("Score: " + Controller.score, 10, 20);

            //Lives
            g2d.drawString("Lives: " + Controller.lives, 380, 20);

            //Time
            g2d.drawString("Time: " + Controller.gameTime, 650, 20);

            g2d.setBackground(backgroundColor);

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
                    g.setColor(new Color(brick.color)); // Set brick color
                    g.fillRect(brick.getPosX(), brick.getPosY(), brick.getWidth(), brick.getHeight()); // Draw brick
                }
            }
        }

        Time gameTime = new Time(Controller.elapsedTimeSeconds * 1000L);
        gameTime.setHours(0);

        //Game over text
        if(Controller.gameOver)
        {
            g2d.drawString("GAME OVER", 370, 310);
            g2d.drawString("Final score: " + Controller.score, 360, 350);
            g2d.drawString("Time: " + gameTime, 360, 390);
        }
        if(Controller.gameWon)
        {
            g2d.drawString("YOU WON", 370, 310);
            g2d.drawString("Final score: " + Controller.score, 360, 350);
            g2d.drawString("Time: " + gameTime, 360, 390);
        }
    }

    public static void tick()
    {
        if(!Controller.gameOver || Controller.gameWon)
        {
            if(isFirstFrame)
            {
                controller.start();
            }
            controller.update(); // Call the controller to update positions
        }
        Toolkit.getDefaultToolkit().sync(); // Ensure smooth rendering
    }
}