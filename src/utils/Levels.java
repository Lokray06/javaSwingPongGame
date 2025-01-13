package utils;

import DTOs.GameModeDTO;

import java.util.HashMap;

public class Levels
{
    public static HashMap<String, GameModeDTO> levels = new HashMap<>();

    public static void init()
    {
        levels.put("Easy", new GameModeDTO(30, 20, 600, new boolean[]{true, true, true, false}, 2, 1, 120, 5, 1));
        levels.put("Medium", new GameModeDTO(50, 20, 300, new boolean[]{true, false, true, false}, 5, 2, 15, 5, 1));
    }
}
