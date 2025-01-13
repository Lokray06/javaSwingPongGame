package DTOs;

public class BrickDTO {
    public int color;      // Brick color (could be an RGB int)
    public int posX, posY; // Position of the brick (top-left corner)
    public int width, height; // Width and height of the brick
    public boolean destroyed; // Whether the brick is destroyed

    // Constructor to initialize the brick
    public BrickDTO(int color, int posX, int posY, int width, int height) {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.destroyed = false; // Initially, the brick is not destroyed
    }

    // Override toString for debugging and easier visualization
    @Override
    public String toString() {
        return "BrickDTO [color=" + color + ", posX=" + posX + ", posY=" + posY + ", width=" + width + ", height=" + height + ", destroyed=" + destroyed + "]";
    }
}
