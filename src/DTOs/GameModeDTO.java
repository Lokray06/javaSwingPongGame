package DTOs;

public class GameModeDTO
{
    int bricks = 0;
    int startingBricks = 0;
    int maxGameTimeSecs = 0;
    boolean[] sidesAllowed = new boolean[]{false, false, false, false};
    int ballSpeed = 0;
    int palettes = 0;
    int spawnBrickCooldownSecs = 0;
    int numberOfBricksEachSpawning;
    int numberOfBalls = 0;
    int lives = 0;
    int paletteSpeed = 0;
    int ballSize;

    public int getPaletteSpeed() {
        return paletteSpeed;
    }

    public int getBallSize() {
        return ballSize;
    }

    public GameModeDTO(int bricks, int startingBricks, int maxGameTimeSecs, boolean[] sidesAllowed, int ballSpeed, int palettes, int spawnBrickCooldown, int numberOfBricksEachSpawning, int numberOfballs, int lives, int paletteSpeed, int ballSize) {
        this.bricks = bricks;
        this.startingBricks = startingBricks;
        this.maxGameTimeSecs = maxGameTimeSecs;
        this.sidesAllowed = sidesAllowed;
        this.ballSpeed = ballSpeed;
        this.palettes = palettes;
        this.spawnBrickCooldownSecs = spawnBrickCooldown;
        this.numberOfBricksEachSpawning = numberOfBricksEachSpawning;
        this.numberOfBalls = numberOfballs;
        this.lives = lives;
        this.paletteSpeed = paletteSpeed;
        this.ballSize = ballSize;
    }

    public int getNumberOfBalls() {
        return numberOfBalls;
    }

    public int getBricks() {
        return bricks;
    }

    public int getStartingBricks() {
        return startingBricks;
    }

    public int getMaxGameTimeSecs() {
        return maxGameTimeSecs;
    }

    public boolean[] getSidesAllowed() {
        return sidesAllowed;
    }

    public int getBallSpeed() {
        return ballSpeed;
    }

    public int getPalettes() {
        return palettes;
    }

    public int getSpawnBrickCooldownSecs() {
        return spawnBrickCooldownSecs;
    }

    public int getNumberOfBricksEachSpawning() {
        return numberOfBricksEachSpawning;
    }
}
