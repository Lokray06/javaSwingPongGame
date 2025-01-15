package utils;

import DTOs.BallDTO;
import DTOs.BrickDTO;
import DTOs.GameModeDTO;
import DTOs.PaletteDTO;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    private List<PaletteDTO> palettes;
    private List<BallDTO> balls;
    public static List<BrickDTO> bricks;
    private GameModeDTO selectedGameMode;

    public static int hInput = 0;
    public static int score = 0;
    private boolean requestSpawn = true;

    public Controller(List<PaletteDTO> palettes, List<BallDTO> balls) {
        this.palettes = palettes;
        this.balls = balls;
    }

    public void start()
    {
        selectedGameMode = GameModeSelectionWindow.selectedGameMode;
        BrickSpawner brickSpawner = new BrickSpawner(50,20, 2, 2);
        int rows = (int) Math.sqrt(selectedGameMode.getStartingBricks());
        int cols = (int) Math.ceil((double) selectedGameMode.getStartingBricks() / rows);
        bricks = brickSpawner.init(rows, cols);
        ScreenHandler.isFirstFrame = false;
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

            // Ball-Brick collision check
            if (ball.checkBrickCollision(bricks)) {
                // Remove the brick from the list (this is done here, but you could be more specific with which brick)
                Iterator<BrickDTO> brickIterator = bricks.iterator();
                while (brickIterator.hasNext()) {
                    BrickDTO brick = brickIterator.next();
                    if (ball.posX + ball.radius > brick.getPosX() && ball.posX - ball.radius < brick.getPosX() + brick.getWidth() &&
                            ball.posY + ball.radius > brick.getPosY() && ball.posY - ball.radius < brick.getPosY() + brick.getHeight()) {
                        // If brick is hit, remove it from the list
                        brickIterator.remove();
                    }
                }
            }

            // Update ball position based on current velocity
            ball.posX += ball.velX;
            ball.posY += ball.velY;
        }

        //Bricks handling
        if(score < GameModeSelectionWindow.selectedGameMode.getBricks() * 10)
        {
            //Hasn't won yet
            if(requestSpawn)
            {
                //TODO spawn

                requestSpawn = false;
                startAsynchronousTimer(GameModeSelectionWindow.selectedGameMode.getSpawnBrickCooldownSecs());
            }
        }
    }

    // Starts an asynchronous timer that executes a task after the specified delay in seconds
    private void startAsynchronousTimer(int delayInSeconds) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                requestSpawn = true;
                timer.cancel(); // Stop the timer after execution
            }
        }, delayInSeconds * 1000);
    }
}
