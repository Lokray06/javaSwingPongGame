package utils;

import DTOs.BallDTO;
import DTOs.BrickDTO;
import DTOs.GameModeDTO;
import DTOs.PaletteDTO;

import java.sql.Time;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    private List<PaletteDTO> palettes;
    private List<BallDTO> balls;
    public static List<BrickDTO> bricks;
    private GameModeDTO selectedGameMode;
    BrickSpawner brickSpawner = new BrickSpawner(60, 30, 2, 2);

    public static int hInput = 0;
    public static int score = 0;
    private boolean requestSpawn = true;
    public static int secondsLeft = 0;
    public static int elapsedTimeSeconds = 0;
    public static Time gameTime;
    public static boolean gameOver = false;
    public static boolean gameWon = false;

    // Static variables
    public static boolean[] passedSides = new boolean[]{false, false, false, false}; // Left, Top, Right, Bottom
    public static int lives = 3; // Starting lives

    // Timer references
    private Timer spawnTimer;
    private Timer countdownTimer;

    public Controller(List<PaletteDTO> palettes, List<BallDTO> balls) {
        this.palettes = palettes;
        this.balls = balls;
        gameTime = new Time(0);
    }

    public void start() {
        selectedGameMode = GameModeSelectionWindow.selectedGameMode;
        int rows = (int) Math.sqrt(selectedGameMode.getStartingBricks());
        int cols = (int) Math.ceil((double) selectedGameMode.getStartingBricks() / rows);
        bricks = brickSpawner.init(rows, cols);
        ScreenHandler.isFirstFrame = false;

        // Set the starting time to the max game time in seconds
        secondsLeft = selectedGameMode.getMaxGameTimeSecs();
        gameTime.setTime(secondsLeft * 1000L); // Set the game time in milliseconds
        countElapsedTimeSeconds(); // Start the countdown

        // Start the spawn delay timer
        requestSpawn = false;
        startAsynchronousTimer(selectedGameMode.getSpawnBrickCooldownSecs()); // Start cooldown immediately
    }

    public void update() {
        // Reset passedSides for each update cycle
        for (int i = 0; i < passedSides.length; i++) {
            passedSides[i] = false; // Reset all sides
        }

        // Update game time
        gameTime.setTime(secondsLeft * 1000L); // Convert seconds to milliseconds
        gameTime.setHours(0);

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
            // Check for ball passing through the sides (without bouncing)
            if (ball.posX < 0 && !selectedGameMode.getSidesAllowed()[0]) { // Left side not allowed
                passedSides[0] = true; // Ball passed through left side
            }
            if (ball.posX + ball.radius > GameWindow.screenX && !selectedGameMode.getSidesAllowed()[2]) { // Right side not allowed
                passedSides[2] = true; // Ball passed through right side
            }
            if (ball.posY < 0 && !selectedGameMode.getSidesAllowed()[1]) { // Top side not allowed
                passedSides[1] = true; // Ball passed through top side
            }
            if (ball.posY + ball.radius > GameWindow.screenY && !selectedGameMode.getSidesAllowed()[3]) { // Bottom side not allowed
                passedSides[3] = true; // Ball passed through bottom side
            }

            // Ball bounce logic based on sidesAllowed
            if (ball.posX < 0 && selectedGameMode.getSidesAllowed()[0]) { // Left side bounce allowed
                ball.velX *= -1;
            }
            if (ball.posX + ball.radius > GameWindow.screenX && selectedGameMode.getSidesAllowed()[2]) { // Right side bounce allowed
                ball.velX *= -1;
            }
            if (ball.posY < 0 && selectedGameMode.getSidesAllowed()[1]) { // Top side bounce allowed
                ball.velY *= -1;
            }
            if (ball.posY + ball.radius > GameWindow.screenY && selectedGameMode.getSidesAllowed()[3]) { // Bottom side bounce allowed
                ball.velY *= -1;
            }

            boolean[] collisions = ball.checkCollision(palettes);
            if (collisions[0]) ball.velX *= -1;
            if (collisions[1]) ball.velY *= -1;

            List<BrickDTO> bricksToRemove = ball.checkBrickCollision(bricks);
            if (!bricksToRemove.isEmpty()) {
                bricks.removeAll(bricksToRemove);
                score += 10 * bricksToRemove.size();
            }

            ball.posX += ball.velX;
            ball.posY += ball.velY;
        }

        // Decrease lives if the ball has passed through any side that is not allowed
        if (anySidePassed()) {
            lives--; // Decrease lives
            score -= 5;
            resetBallPositions(); // Optionally reset ball positions
        }

        // Check for game over condition
        if (lives <= 0) {
            endGame();
        }

        if (BrickSpawner.totalNumberOfBricksSpawned >= selectedGameMode.getBricks() && bricks.isEmpty())
        {
            gameWon = true;
        }

        // Spawn bricks only if the cooldown has passed
        if (requestSpawn && BrickSpawner.totalNumberOfBricksSpawned < selectedGameMode.getBricks()) {
            int bricksToSpawn = 0;
            if(BrickSpawner.totalNumberOfBricksSpawned + selectedGameMode.getNumberOfBricksEachSpawning() <= selectedGameMode.getBricks()) {
                bricksToSpawn = selectedGameMode.getNumberOfBricksEachSpawning();
            } else {
                bricksToSpawn = selectedGameMode.getNumberOfBricksEachSpawning() - (selectedGameMode.getBricks() - BrickSpawner.totalNumberOfBricksSpawned);
            }
            brickSpawner.spawnNextToExistingBricks(bricksToSpawn, bricks);
            System.out.println("Total bricks spawned: " + BrickSpawner.totalNumberOfBricksSpawned);
            System.out.println("Spawned " + bricksToSpawn + " new bricks.");
            requestSpawn = false;
            startAsynchronousTimer(selectedGameMode.getSpawnBrickCooldownSecs()); // Reset cooldown
        }

        elapsedTimeSeconds = selectedGameMode.getMaxGameTimeSecs() - secondsLeft;
    }

    // Check if any side has been passed by the ball that is not allowed
    private boolean anySidePassed() {
        for (int i = 0; i < passedSides.length; i++) {
            if (passedSides[i] && !selectedGameMode.getSidesAllowed()[i]) {
                return true; // Ball passed through a side that is not allowed
            }
        }
        return false; // No sides passed that are not allowed
    }

    private void resetBallPositions() {
        // Temporarily stop the ball's movement by setting velocities to 0
        for (BallDTO ball : balls) {
            ball.posX = GameWindow.screenX / 2; // Reset ball to center
            ball.posY = GameWindow.screenY / 2;
            ball.velX = 0; // Stop the ball temporarily
            ball.velY = 0;
            System.out.println(ball.posX + ", " + ball.posY);

            // Pause the ball for 2 seconds before continuing
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    // After the pause, set the ball's speed
                    ball.velX = selectedGameMode.getBallSpeed() * Random.direction();
                    ball.velY = selectedGameMode.getBallSpeed();
                }
            }, 2000); // Wait for 2 seconds before continuing ball movement
        }
    }

    // End the game when lives reach 0
    private void endGame() {
        // You can add logic here for ending the game, such as displaying a game over screen
        //Prevent any more calls to GameWindow.tick();
        
        gameOver = true;
        if (spawnTimer != null) {
            spawnTimer.cancel(); // Stop the brick spawn timer
        }
        if (countdownTimer != null) {
            countdownTimer.cancel(); // Stop the countdown timer
        }
    }

    // Starts an asynchronous timer for spawning bricks
    private void startAsynchronousTimer(int delayInSeconds) {
        spawnTimer = new Timer();
        spawnTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!gameWon && !gameOver) {
                    requestSpawn = true;
                }
            }
        }, delayInSeconds * 1000L);
    }

    private void countElapsedTimeSeconds() {
        countdownTimer = new Timer();
        countdownTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!gameWon && !gameOver) {
                    if (secondsLeft > 0) {
                        secondsLeft--; // Decrease the time
                        gameTime.setTime(secondsLeft * 1000L); // Update the game time
                    } else {
                        endGame(); // Time is up, end the game
                        countdownTimer.cancel(); // Stop the countdown timer
                    }
                }
            }
        }, 0, 1000); // Schedule to run every second
    }
}
