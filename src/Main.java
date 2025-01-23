import utils.ColorPalettes;
import utils.GameModeSelectionWindow;
import utils.Levels;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Initialize levels
        Levels.init();
        ColorPalettes.init();
        
        // Create and show the game mode selection window
        SwingUtilities.invokeLater(() -> new GameModeSelectionWindow());
    }
}
