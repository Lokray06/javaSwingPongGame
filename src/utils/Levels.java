package utils;

import DTOs.GameModeDTO;

import java.util.HashMap;

public class Levels
{
    public static HashMap<String, GameModeDTO> levels = new HashMap<>();

    public static void init()
    {
        levels.put("Easy", new GameModeDTO(30, 20, 600, new boolean[]{true, true, true, false}, 4, 1, 120, 5, 1, 3, 6, 50));
        levels.put("Medium", new GameModeDTO(50, 20, 300, new boolean[]{true, false, true, false}, 5, 2, 15, 5, 1, 3, 6, 30));
        levels.put("Cursed", new GameModeDTO(500, 40, 700, new boolean[]{true, false, true, false}, 6, 2, 20, 10, 1, 3, 7, 10));
    }
}
