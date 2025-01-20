package utils;

import DTOs.BrickDTO;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BrickSpawner {
    private int brickWidth;     // Width of a brick
    private int brickHeight;    // Height of a brick
    private int horizontalSpacing; // Space between bricks horizontally
    private int verticalSpacing;   // Space between bricks vertically
    public static int totalNumberOfBricksSpawned;

    // Constructor to initialize the spawner with grid settings
    public BrickSpawner(int brickWidth, int brickHeight, int horizontalSpacing, int verticalSpacing) {
        this.brickWidth = brickWidth;
        this.brickHeight = brickHeight;
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
    }

    // Method to spawn bricks in a grid-like shape with proper spacing
    public List<BrickDTO> init(int rows, int columns) {
        List<BrickDTO> bricks = new ArrayList<>();

        int screenWidth = GameWindow.screenX;
        int screenHeight = GameWindow.screenY;
        int marginX = GameWindow.marginX;
        int marginY = GameWindow.marginY + 20;

        // Adjusting brick width and height for spacing
        brickWidth = (screenWidth - 2 * marginX - (columns - 1) * horizontalSpacing) / columns;
        brickHeight = (screenHeight / 3 - marginY - (rows - 1) * verticalSpacing) / rows;

        // Create bricks in a grid-like pattern with spacing
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                int posX = marginX + (brickWidth + horizontalSpacing) * col;
                int posY = marginY + (brickHeight + verticalSpacing) * row;

                // Random color for each brick (can be customized)
                int color = (int) (0x000000);

                // Create the brick and add to the list
                BrickDTO brick = new BrickDTO(posX, posY, brickWidth, brickHeight, 10, 10);
                bricks.add(brick);
            }
        }

        return bricks; // Return the list of spawned bricks
    }

    public void spawnNextToExistingBricks(int numBricks, List<BrickDTO> bricks) {
        if (bricks.isEmpty()) {
            // If no bricks exist, spawn the first brick at a random position.
            for (int i = 0; i < numBricks; i++) {
                int x = Random.randomInGrid(brickWidth, brickHeight, bricks)[0];
                int y = Random.randomInGrid(brickWidth, brickHeight, bricks)[1];
                bricks.add(new BrickDTO(x, y, brickWidth, brickHeight, 1, 100));
            }
            return;
        }

        for (int i = 0; i < numBricks; i++) {
            // Select a random existing brick
            BrickDTO existingBrick = bricks.get(Random.inRange(0, bricks.size() - 1));

            // Determine a random adjacent position
            int direction = Random.inRange(0, 3); // 0 = top, 1 = bottom, 2 = left, 3 = right
            int x = existingBrick.getPosX();
            int y = existingBrick.getPosY();

            switch (direction) {
                case 0: // Top
                    y -= brickHeight + verticalSpacing;
                    break;
                case 1: // Bottom
                    y += brickHeight + verticalSpacing;
                    break;
                case 2: // Left
                    x -= brickWidth + horizontalSpacing;
                    break;
                case 3: // Right
                    x += brickWidth + horizontalSpacing;
                    break;
            }

            // Check for overlap with existing bricks
            Rectangle newBrickBounds = new Rectangle(x, y, brickWidth, brickHeight);
            boolean overlaps = bricks.stream()
                    .anyMatch(brick -> new Rectangle(brick.getPosX(), brick.getPosY(), brickWidth, brickHeight)
                            .intersects(newBrickBounds));

            if (!overlaps && x >= 0 && y >= 0 && x + brickWidth <= 800 && y + brickHeight <= 600) {
                // Add the new brick if it doesn't overlap and is within bounds
                bricks.add(new BrickDTO(x, y, brickWidth, brickHeight, 1, 100));
            } else {
                // Retry this brick if placement failed
                i--;
            }
        }
    }
}
