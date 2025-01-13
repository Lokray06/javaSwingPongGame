package utils;

import DTOs.BallDTO;
import DTOs.PaletteDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ScreenHandler extends JPanel {
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

        // Draw all the rectangles based on the palettes list
        for (PaletteDTO palette : palettes) {
            g.setColor(new Color(palette.color)); // Set color
            g.fillRect(palette.posX, palette.posY, palette.width, palette.height); // Draw rectangle using width and height
        }
        //Draw all the balls
        for (BallDTO ball : balls) {
            g.setColor(new Color(ball.color)); // Set color
            g.fillOval(ball.posX, ball.posY, ball.radius, ball.radius); // Draw rectangle using width and height
        }
    }

    public static void updatePositions() {
        controller.update(); // Call the controller to update positions
        Toolkit.getDefaultToolkit().sync(); // Ensure smooth rendering
    }
}

/*
package utils;

import DTOs.BrickDTO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ScreenHandler extends JPanel {
    private List<BrickDTO> bricks;  // List of bricks to be drawn

    public ScreenHandler() {
        // Initialize the brick spawner and spawn bricks
        BrickSpawner brickSpawner = new BrickSpawner(50, 20, 10, 10);  // 50x20 bricks, with 10px spacing
        bricks = brickSpawner.spawnBricks(5, 10);  // Create 5 rows and 10 columns of bricks

        setPreferredSize(new Dimension(800, 600)); // Set the screen size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw all the bricks
        for (BrickDTO brick : bricks) {
            g.setColor(new Color(brick.color));  // Set brick color
            g.fillRect(brick.posX, brick.posY, brick.width, brick.height);  // Draw brick
        }
    }
}

 */