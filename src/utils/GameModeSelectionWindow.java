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
        String[] gameModes = {"Easy", "Medium", "Custom"};
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

            int paletteWidth = 130;  // Define the width of the palette
            int paletteHeight = 40; // Define the height of the palette

            // Create palettes based on the number specified in the selected game mode
            if (selectedGameMode.getPalettes() == 1) {
                // One palette at y = 500 (bottom-left corner placement)
                palettes.add(new PaletteDTO(Color.BLACK.getRGB(), 400, 550 - paletteHeight, 5, paletteWidth, paletteHeight));
            } else if (selectedGameMode.getPalettes() == 2) {
                // Two palettes, one at y = 500 and the other at y = 50 (bottom-left corner placement)
                palettes.add(new PaletteDTO(Color.BLACK.getRGB(), 400, 550 - paletteHeight, 5, paletteWidth, paletteHeight));
                palettes.add(new PaletteDTO(Color.BLACK.getRGB(), 400, 550 - paletteHeight - 470, 5, paletteWidth, paletteHeight));
            }

            List<BallDTO> balls = new ArrayList<>();
            for(int i = 0; i < selectedGameMode.getNumberOfBalls(); i++)
            {
                balls.add(new BallDTO(Color.BLACK.getRGB(), 30, 600, 10, selectedGameMode.getBallSpeed(), 0, 0));
            }

            // Close the game mode selection window
            frame.dispose();

            // Start the game with the selected game mode and palettes
            SwingUtilities.invokeLater(() -> new GameWindow(palettes, balls));
        });

        frame.pack();
        frame.setVisible(true);
    }
}
