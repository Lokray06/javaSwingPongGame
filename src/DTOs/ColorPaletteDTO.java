package DTOs;

public class ColorPaletteDTO
{
    int id;

    String[] colors = new String[5];

    public ColorPaletteDTO(String c1, String c2, String c3, String c4, String c5)
    {
        this.colors[0] = c1;
        this.colors[1] = c2;
        this.colors[2] = c3;
        this.colors[3] = c4;
        this.colors[4] = c5;
    }

    public String[] getColors() {
        return colors;
    }
}
