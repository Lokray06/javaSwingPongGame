package utils;

import DTOs.ColorPaletteDTO;
import DTOs.GameModeDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ColorPalettes
{
    public static List<ColorPaletteDTO> colorPalettes = new ArrayList<>();

    public static void init()
    {
        colorPalettes.add(new ColorPaletteDTO("E8CA90", "B99D95", "DDCE6B", "B0356C", "20142A"));
    }
}
