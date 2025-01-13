package DTOs;

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

    // Method to check for collisions with the list of palettes
    public boolean[] checkCollision(List<PaletteDTO> palettes) {
        boolean[] collisionResult = new boolean[2]; // [0] = left/right collision, [1] = top/bottom collision

        // Loop through each palette and check for collision
        for (PaletteDTO palette : palettes) {
            // Check if the ball is within the horizontal and vertical bounds of the palette
            if (this.posX + this.radius > palette.posX && this.posX - this.radius < palette.posX + palette.width &&
                    this.posY + this.radius > palette.posY && this.posY - this.radius < palette.posY + palette.height) {

                // Horizontal collision detection (left or right)
                if (this.posX < palette.posX) {
                    // Ball is on the left of the palette
                    collisionResult[0] = true;  // Left collision
                } else if (this.posX > palette.posX + palette.width) {
                    // Ball is on the right of the palette
                    collisionResult[0] = true;  // Right collision
                }

                // Vertical collision detection (top or bottom)
                if (this.posY < palette.posY) {
                    // Ball is above the palette
                    collisionResult[1] = true;  // Top collision
                } else if (this.posY > palette.posY + palette.height) {
                    // Ball is below the palette
                    collisionResult[1] = true;  // Bottom collision
                }

                // Only flip one velocity based on which side of the palette the ball collided with
                if (collisionResult[0] && collisionResult[1]) {
                    // If both horizontal and vertical collisions are true, prioritize vertical collision (for bounce).
                    collisionResult[0] = false;
                }

                return collisionResult; // Return once a collision is found
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
