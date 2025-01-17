package utils;

import DTOs.BrickDTO;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Random {
    // Existing inRange method
    public static int inRange(int n1, int n2) {
        if (n1 > n2) {
            throw new IllegalArgumentException("n1 should not be greater than n2");
        }
        return ThreadLocalRandom.current().nextInt(n1, n2 + 1);
    }

    public static int direction() {
        // Return either -1 or 1 randomly
        int newDirection = Math.random() < 0.5 ? -1 : 1;
        System.out.println(newDirection);
        return newDirection;
    }


    public static int[] randomInGrid(int brickWidth, int brickHeight, List<BrickDTO> existingBricks) {
        // Calculate the grid limits considering the margins and brick sizes
        int xMin = GameWindow.marginX;
        int xMax = GameWindow.screenX - GameWindow.marginX - brickWidth;

        // Calculate the available rows, considering brick height and vertical spacing
        int rows = (GameWindow.screenY - 2 * GameWindow.marginY) / (brickHeight + 5); // Number of rows that fit

        // Select a random row, with correct spacing between rows
        int randomRow = Random.inRange(2, rows - 1);  // Start from row 1 to avoid the first row

        // Calculate the Y position of the brick based on the row
        int y = GameWindow.marginY + randomRow * (brickHeight + 5);

        // Variables to hold the new brick's position
        int x;

        int separation = 5; // Fixed separation between bricks

        boolean isValidPosition;

        do {
            // Generate a random X position within the valid range, considering spacing
            x = inRange(xMin, xMax);

            // Ensure separation between this new brick and existing bricks
            isValidPosition = true;

            // Check all existing bricks to ensure separation
            for (BrickDTO brick : existingBricks) {
                // Check if the new brick is too close to any existing brick
                if (Math.abs(brick.getPosX() - x) < brickWidth + separation ||
                        Math.abs(brick.getPosY() - y) < brickHeight + separation) {
                    isValidPosition = false;
                    break;
                }
            }

            // Ensure the brick does not spawn in the first row
            if (y <= GameWindow.marginY + brickHeight + 5) {
                isValidPosition = false;
            }

        } while (!isValidPosition);  // Keep trying until a valid position is found

        return new int[] {x, y};
    }
}
