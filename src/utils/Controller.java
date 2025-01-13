package utils;

import DTOs.BallDTO;
import DTOs.PaletteDTO;

import java.util.List;

public class Controller {

    private List<PaletteDTO> palettes;
    private List<BallDTO> balls;

    public static int hInput = 0;

    public Controller(List<PaletteDTO> palettes, List<BallDTO> balls) {
        this.palettes = palettes;
        this.balls = balls;
    }

    // Update method for game logic
    public void update() {
        // Move palettes
        for (PaletteDTO palette : palettes) {
            palette.posX += palette.moveSpeed * hInput;
            if (palette.posX < 0) {
                palette.posX = 0;
            }
            if (palette.posX > 800 - palette.width) { // 800 width - 50 (palette width)
                palette.posX = 800 - palette.width; // Prevent going off the right side
            }
        }

        // Move the ball
        for (BallDTO ball : balls) {

            // Ball bouncing off the walls logic
            if (ball.posX < 0 || ball.posX + ball.radius > GameWindow.screenX) {
                ball.velX *= -1; // Reverse horizontal velocity when hitting left or right edges
            }
            if (ball.posY < 0 || ball.posY + ball.radius > GameWindow.screenY) {
                ball.velY *= -1; // Reverse vertical velocity when hitting top or bottom edges
            }

            //Collisions with palettes
            boolean[] collisions = ball.checkCollision(palettes);
            if(collisions[0])
            {
                ball.velX *= -1;
            }
            if(collisions[1])
            {
                ball.velY *= -1;
            }

            // Update ball position based on current velocity
            ball.posX += ball.velX;
            ball.posY += ball.velY;
        }
    }

}
