package DTOs;

import utils.BrickSpawner;

public class BrickDTO {
    public int color;
    private int posX, posY;
    private int width, height;
    private int health;
    private int score;


    // Constructor
    public BrickDTO(int color, int posX, int posY, int width, int height, int health, int score) {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.health = health;
        this.score = score;
        BrickSpawner.totalNumberOfBricksSpawned++;
    }

    // Getters for brick properties
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
