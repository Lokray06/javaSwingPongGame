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

    // Method to spawn (n) bricks in a grid-like shape
    public List<BrickDTO> spawnBricks(int rows, int columns) {
        List<BrickDTO> bricks = new ArrayList<>();

        // Calculate the starting position for the first brick (top-left corner of the grid)
        int startX = 50; // Arbitrary starting X position
        int startY = 50; // Arbitrary starting Y position

        // Create bricks in a grid-like pattern
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int posX = startX + (brickWidth + horizontalSpacing) * col;
                int posY = startY + (brickHeight + verticalSpacing) * row;

                // Random color for each brick (can be customized)
                int color = (int) (Math.random() * 0xFFFFFF);  // Random RGB color

                // Create the brick and add to the list
                BrickDTO brick = new BrickDTO(color, posX, posY, brickWidth, brickHeight);
                bricks.add(brick);
            }
        }

        return bricks; // Return the list of spawned bricks
    }
}
