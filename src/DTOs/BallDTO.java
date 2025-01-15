package DTOs;

import utils.Controller;
import utils.GameWindow;
import utils.Random;
import utils.ScreenHandler;

import java.util.ArrayList;
import java.util.List;

public class BallDTO {
    public int color;
    public int radius = 0;
    public int posX = 0;
    public int posY = 0;
    public int moveSpeed = 0;
    public int velX = 0;
    public int velY = 0;

    public BallDTO(int color, int radius, int posX, int posY, int moveSpeed, int velX, int velY) {
        this.color = color;
        this.radius = radius;
        this.posX = posX;
        this.posY = posY;
        this.moveSpeed = moveSpeed;
        this.velX = moveSpeed;
        this.velY = moveSpeed;
    }

    public boolean checkBrickCollision(List<BrickDTO> bricks) {
        List<BrickDTO> bricksToRemove = new ArrayList<>();

        for (BrickDTO brick : bricks) {
            // Calculate bounds of the ball
            float ballLeft = this.posX;
            float ballRight = this.posX + this.radius;
            float ballTop = this.posY;
            float ballBottom = this.posY + this.radius;

            // Calculate bounds of the brick
            float brickLeft = brick.getPosX();
            float brickRight = brick.getPosX() + brick.getWidth();
            float brickTop = brick.getPosY();
            float brickBottom = brick.getPosY() + brick.getHeight();

            // Check if ball intersects the brick
            if (ballRight > brickLeft && ballLeft < brickRight &&
                    ballBottom > brickTop && ballTop < brickBottom) {

                // Calculate the overlap on each side
                float overlapLeft = ballRight - brickLeft;
                float overlapRight = brickRight - ballLeft;
                float overlapTop = ballBottom - brickTop;
                float overlapBottom = brickBottom - ballTop;

                // Find the smallest overlap to determine collision side
                float minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

                // Determine which side of the brick was hit
                if (minOverlap == overlapLeft || minOverlap == overlapRight) {
                    velX *= -1; // Reverse horizontal velocity (left or right collision)
                } else if (minOverlap == overlapTop || minOverlap == overlapBottom) {
                    velY *= -1; // Reverse vertical velocity (top or bottom collision)
                }

                // Add the brick to the removal list
                bricksToRemove.add(brick);
            }
        }

        // Remove the bricks outside the loop
        bricks.removeAll(bricksToRemove);

        // If bricks were removed, update the score
        if (!bricksToRemove.isEmpty()) {
            Controller.score += 10 * bricksToRemove.size(); // Add points for each broken brick
            return true; // Collision has occurred
        }

        return false; // No collision
    }

    // Method to check for collisions with the list of palettes
    public boolean[] checkCollision(List<PaletteDTO> palettes) {
        boolean[] collisionResult = new boolean[2]; // [0] = left/right collision, [1] = top/bottom collision

        // Loop through each palette and check for collision
        for (PaletteDTO palette : palettes) {
            // Calculate bounds of the ball
            float ballLeft = this.posX - this.radius;
            float ballRight = this.posX + this.radius;
            float ballTop = this.posY - this.radius;
            float ballBottom = this.posY + this.radius;

            // Calculate bounds of the palette
            float paletteLeft = palette.posX;
            float paletteRight = palette.posX + palette.width;
            float paletteTop = palette.posY;
            float paletteBottom = palette.posY + 10;

            // Check if ball intersects the palette
            if (ballRight > paletteLeft && ballLeft < paletteRight &&
                    ballBottom > paletteTop && ballTop < paletteBottom) {

                // Determine the collision side
                float overlapLeft = ballRight - paletteLeft;
                float overlapRight = paletteRight - ballLeft;
                float overlapTop = ballBottom - paletteTop;
                float overlapBottom = paletteBottom - ballTop;

                // Find the smallest overlap to determine collision side
                float minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

                if (minOverlap == overlapLeft) {
                    collisionResult[0] = true; // Left collision
                } else if (minOverlap == overlapRight) {
                    collisionResult[0] = true; // Right collision
                } else if (minOverlap == overlapTop) {
                    collisionResult[1] = true; // Top collision
                } else if (minOverlap == overlapBottom) {
                    collisionResult[1] = true; // Bottom collision
                }

                // Return immediately after detecting a collision
                return collisionResult;
            }
        }

        return collisionResult; // No collision detected
    }


    @Override
    public String toString() {
        return "BallDTO {" +
                "color=" + color +
                ", radius=" + radius +
                ", posX=" + posX +
                ", posY=" + posY +
                ", moveSpeed=" + moveSpeed +
                ", velX=" + velX +
                ", velY=" + velY +
                '}';
    }
}
