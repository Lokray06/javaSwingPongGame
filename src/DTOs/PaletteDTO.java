package DTOs;

public class PaletteDTO {

    public int color;
    public int posX, posY;
    public int width, height;
    public int moveSpeed;

    // Constructor including width and height
    public PaletteDTO(int color, int posX, int posY, int moveSpeed, int width, int height) {
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.moveSpeed = moveSpeed;
        this.width = width;
        this.height = height;
    }
}