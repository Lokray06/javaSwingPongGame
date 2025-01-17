package DTOs;

import utils.Random;

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
        this.velX = moveSpeed * Random.direction();
        this.velY = moveSpeed * Random.direction();
    }

    public List<BrickDTO> checkBrickCollision(List<BrickDTO> bricks) {
        List<BrickDTO> bricksToRemove = new ArrayList<>();
        boolean reverseX = false;
        boolean reverseY = false;

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
                    reverseX = true; // Mark for horizontal velocity reversal
                }
                if (minOverlap == overlapTop || minOverlap == overlapBottom) {
                    reverseY = true; // Mark for vertical velocity reversal
                }

                // Add the brick to the removal list
                bricksToRemove.add(brick);
            }
        }

        // Reverse velocities if collisions occurred
        if (reverseX) velX *= -1;
        if (reverseY) velY *= -1;

        // Return the list of bricks to be removed
        return bricksToRemove;
    }

    public boolean[] checkCollision(List<PaletteDTO> palettes) {
        boolean[] collisionResult = new boolean[2]; // [0] = left/right collision, [1] = top/bottom collision

        for (PaletteDTO palette : palettes) {
            float ballLeft = this.posX;
            float ballRight = this.posX + this.radius;
            float ballTop = this.posY - (this.radius / 2);
            float ballBottom = this.posY + this.radius + 3;

            float paletteLeft = palette.posX;
            float paletteRight = palette.posX + palette.width;
            float paletteTop = palette.posY;
            float paletteBottom = palette.posY + 10;

            if (ballRight > paletteLeft && ballLeft < paletteRight &&
                    ballBottom > paletteTop && ballTop < paletteBottom) {

                float overlapLeft = ballRight - paletteLeft;
                float overlapRight = paletteRight - ballLeft;
                float overlapTop = ballBottom - paletteTop;
                float overlapBottom = paletteBottom - ballTop;

                float minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

                if (minOverlap == overlapLeft || minOverlap == overlapRight) {
                    collisionResult[0] = true; // Left/Right collision
                }
                if (minOverlap == overlapTop || minOverlap == overlapBottom) {
                    collisionResult[1] = true; // Top/Bottom collision
                }

                return collisionResult;
            }
        }

        return collisionResult;
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
