package utils;

import DTOs.BallDTO;
import DTOs.GameModeDTO;
import DTOs.PaletteDTO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameModeSelectionWindow {

    private JFrame frame;
    private JComboBox<String> gameModeDropdown;
    private JButton startButton;
    public static GameModeDTO selectedGameMode = null;

    public GameModeSelectionWindow() {
        frame = new JFrame("Select Game Mode");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Create a dropdown for selecting game mode
        String[] gameModes = getLevels();
        gameModeDropdown = new JComboBox<>(gameModes);
        frame.add(gameModeDropdown);

        // Create a start button to begin the game after selecting the mode
        startButton = new JButton("Start Game");
        frame.add(startButton);

        startButton.addActionListener(e -> {
            // Get the selected game mode
            String selectedMode = (String) gameModeDropdown.getSelectedItem();
            selectedGameMode = Levels.levels.get(selectedMode);

            // Initialize the palettes based on the selected game mode
            List<PaletteDTO> palettes = new ArrayList<>();

            int paletteWidth = 110;  // Define the width of the palette
            int paletteHeight = 20; // Define the height of the palette

            // Create palettes based on the number specified in the selected game mode
            if (selectedGameMode.getPalettes() == 1) {
                // One palette at y = 500 (bottom-left corner placement)
                palettes.add(new PaletteDTO(Color.BLACK.getRGB(), GameWindow.screenX/2, GameWindow.screenY-20 - paletteHeight, selectedGameMode.getPaletteSpeed(), paletteWidth, paletteHeight));
            } else if (selectedGameMode.getPalettes() == 2) {
                // Two palettes, one at y = 500 and the other at y = 50 (bottom-left corner placement)
                palettes.add(new PaletteDTO(Color.BLACK.getRGB(), GameWindow.screenX/2, 20,  selectedGameMode.getPaletteSpeed(), paletteWidth, paletteHeight));
                palettes.add(new PaletteDTO(Color.BLACK.getRGB(), GameWindow.screenX/2, GameWindow.screenY-20 - paletteHeight,  selectedGameMode.getPaletteSpeed(), paletteWidth, paletteHeight));
            }

            List<BallDTO> balls = new ArrayList<>();
            for(int i = 0; i < selectedGameMode.getNumberOfBalls(); i++)
            {
                balls.add(new BallDTO(Color.BLACK.getRGB(), selectedGameMode.getBallSize(), GameWindow.screenX/2, GameWindow.screenY/2, selectedGameMode.getBallSpeed(), 0, 0));
            }

            // Close the game mode selection window
            frame.dispose();

            // Start the game with the selected game mode and palettes
            SwingUtilities.invokeLater(() -> new GameWindow(palettes, balls));
        });

        frame.pack();
        frame.setVisible(true);
    }

    private String[] getLevels() {
        // Create an array to store the level names (keys)
        String[] levelNames = new String[Levels.levels.size()];

        // Populate the array with the keys (level names) from the levels map
        int index = 0;
        for (String levelName : Levels.levels.keySet()) {
            levelNames[index++] = levelName;
        }

        return levelNames;
    }
}
