package DTOs;

public class GameModeDTO
{
    String gameModeName = "aaaaa";
    int bricks = 0;
    int startingBricks = 0;
    int maxGameTimeSecs = 0;
    boolean[] sidesAllowed = new boolean[]{false, false, false, false};
    int ballSpeed = 0;
    int palettes = 0;
    int spawnBrickCooldownSecs = 0;
    int numberOfBricksEachSpawning;
    int numberOfBalls = 0;

    public GameModeDTO(int bricks, int startingBricks, int maxGameTimeSecs, boolean[] sidesAllowed, int ballSpeed, int palettes, int spawnBrickCooldown, int numberOfBricksEachSpawning, int numberOfballs) {
        this.bricks = bricks;
        this.startingBricks = startingBricks;
        this.maxGameTimeSecs = maxGameTimeSecs;
        this.sidesAllowed = sidesAllowed;
        this.ballSpeed = ballSpeed;
        this.palettes = palettes;
        this.spawnBrickCooldownSecs = spawnBrickCooldown;
        this.numberOfBricksEachSpawning = numberOfBricksEachSpawning;
        this.numberOfBalls = numberOfballs;
    }

    public int getNumberOfBalls() {
        return numberOfBalls;
    }

    public void setNumberOfBalls(int numberOfBalls) {
        this.numberOfBalls = numberOfBalls;
    }

    public String getGameModeName() {
        return gameModeName;
    }

    public void setGameModeName(String gameModeName) {
        this.gameModeName = gameModeName;
    }

    public int getBricks() {
        return bricks;
    }

    public void setBricks(int bricks) {
        this.bricks = bricks;
    }

    public int getStartingBricks() {
        return startingBricks;
    }

    public void setStartingBricks(int startingBricks) {
        this.startingBricks = startingBricks;
    }

    public int getMaxGameTimeSecs() {
        return maxGameTimeSecs;
    }

    public void setMaxGameTimeSecs(int maxGameTimeSecs) {
        this.maxGameTimeSecs = maxGameTimeSecs;
    }

    public boolean[] getSidesAllowed() {
        return sidesAllowed;
    }

    public void setSidesAllowed(boolean[] sidesAllowed) {
        this.sidesAllowed = sidesAllowed;
    }

    public int getBallSpeed() {
        return ballSpeed;
    }

    public void setBallSpeed(int ballSpeed) {
        this.ballSpeed = ballSpeed;
    }

    public int getPalettes() {
        return palettes;
    }

    public void setPalettes(int palettes) {
        this.palettes = palettes;
    }

    public int getSpawnBrickCooldownSecs() {
        return spawnBrickCooldownSecs;
    }

    public void setSpawnBrickCooldownSecs(int spawnBrickCooldownSecs) {
        this.spawnBrickCooldownSecs = spawnBrickCooldownSecs;
    }

    public int getNumberOfBricksEachSpawning() {
        return numberOfBricksEachSpawning;
    }

    public void setNumberOfBricksEachSpawning(int numberOfBricksEachSpawning) {
        this.numberOfBricksEachSpawning = numberOfBricksEachSpawning;
    }
}
