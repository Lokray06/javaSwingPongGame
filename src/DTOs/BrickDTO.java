package DTOs;

import utils.BrickSpawner;
import utils.ScreenHandler;

import java.awt.*;

public class BrickDTO {
    public Color color;
    private int posX, posY;
    private int width, height;

    // Constructor
    public BrickDTO(int posX, int posY, int width, int height, int a, int b) {
        this.color = ScreenHandler.colorBrick;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
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
