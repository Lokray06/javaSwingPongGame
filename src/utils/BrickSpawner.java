package utils;

import DTOs.BrickDTO;
import java.util.ArrayList;
import java.util.List;

public class BrickSpawner {
    private int brickWidth;     // Width of a brick
    private int brickHeight;    // Height of a brick
    private int horizontalSpacing; // Space between bricks horizontally
    private int verticalSpacing;   // Space between bricks vertically

    // Constructor to initialize the spawner with grid settings
    public BrickSpawner(int brickWidth, int brickHeight, int horizontalSpacing, int verticalSpacing) {
        this.brickWidth = brickWidth;
        this.brickHeight = brickHeight;
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
    }

    // Method to spawn bricks in a grid-like shape
    public List<BrickDTO> init(int rows, int columns) {
        List<BrickDTO> bricks = new ArrayList<>();

        int screenWidth = GameWindow.screenX;
        int screenHeight = GameWindow.screenY;
        int marginX = GameWindow.marginX;
        int marginY = GameWindow.marginY;

        // Calculate dimensions and spacing automatically
        brickWidth = (screenWidth - 2 * marginX) / columns - 5;
        brickHeight = (screenHeight / 3 - marginY) / rows - 5;
        horizontalSpacing = 5; // Fixed horizontal spacing
        verticalSpacing = 5;   // Fixed vertical spacing

        // Create bricks in a grid-like pattern
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int posX = marginX + (brickWidth + horizontalSpacing) * col;
                int posY = marginY + (brickHeight + verticalSpacing) * row;

                // Random color for each brick (can be customized)
                int color = (int) (0x000000);

                // Create the brick and add to the list
                BrickDTO brick = new BrickDTO(color, posX, posY, brickWidth, brickHeight, 10, 10);
                bricks.add(brick);
            }
        }

        return bricks; // Return the list of spawned bricks
    }
}
