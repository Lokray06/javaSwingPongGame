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
        levels.put("TEST", new GameModeDTO(1000, 40, 100000, new boolean[]{true, true, true, true}, 10, 1, 5,2, 1, 1, 10, 20));
        
        //made by chatgpt
        levels.put("Chaotic", new GameModeDTO(100, 50, 180, new boolean[]{true, true, true, false}, 10, 1, 10, 10, 5, 3, 8, 30));
        levels.put("Precision", new GameModeDTO(20, 20, 600, new boolean[]{true, false, true, false}, 4, 2, 60, 1, 1, 5, 5, 40));
        levels.put("Endurance", new GameModeDTO(200, 30, 1200, new boolean[]{true, false, true, false}, 6, 2, 20, 5, 1, 10, 7, 25));
        levels.put("Wild Bounce", new GameModeDTO(80, 40, 300, new boolean[]{true, true, true, true}, 12, 1, 15, 3, 8, 1, 10, 20));
        levels.put("Puzzle", new GameModeDTO(10, 10, 900, new boolean[]{true, false, true, false}, 3, 2, 90, 2, 1, 10, 5, 50));
        
        
    }
}
