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
