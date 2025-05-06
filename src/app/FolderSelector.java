package app;

import javax.swing.*;
import java.io.File;

public class FolderSelector {
    private JFileChooser chooser;
    private int result;
    private File latestFolder = null;

    public FolderSelector () {
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Allow only folders
    }

    public File promptFolderSelect () {
        result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = chooser.getSelectedFile();
            latestFolder = selectedFolder;
            System.out.println("Selected folder: " + selectedFolder.getAbsolutePath());
            return selectedFolder;
        } else {
            System.out.println("No folder selected.");
            return null;
        }
    }

    public File getLatestFolder () {
        return latestFolder;
    }

}