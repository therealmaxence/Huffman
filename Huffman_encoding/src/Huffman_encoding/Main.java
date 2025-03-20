package Huffman_encoding;

import java.util.List;
import java.util.Map.Entry;

import javax.swing.SwingUtilities;

/**
 * Main class to launch the Huffman Compression application.
 * This initializes the graphical user interface (GUI) for the program.
 * WARNING: All the docstrings where AI-generated for time-efficiency purposes.
 */
public class Main {

    /**
     * The main entry point of the application.
     * It initializes the user interface within the Swing event dispatch thread.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserInterface ui = new UserInterface();
            ui.setVisible(true);
        });
    }
}
