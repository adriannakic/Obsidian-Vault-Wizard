import app.FileCollector;
import app.FolderSelector;
import app.ObsidianVault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public static void main (String[] args) {
    System.out.println("Starting up...");

    // user picks out vault folder
    FolderSelector folderSelector = new FolderSelector();
    System.out.println("Pick out your vault...");
    folderSelector.promptFolderSelect();

    // traverse vault folder for files
    String vaultPath = folderSelector.getLatestFolder().getAbsolutePath();
    FileCollector fileCollector = new FileCollector(vaultPath);
    ArrayList<File> vaultFiles = fileCollector.getFileList();
    System.out.println(fileCollector);

    // create model obsidian vault object
    ObsidianVault obsidianVault = new ObsidianVault();
    obsidianVault.setRootFolder(new File(vaultPath));
    obsidianVault.setMarkdownFiles(vaultFiles);

    // replace all obsidian note names harmed by onedrive process
    String removeTerm = "-DESKTOP-KBP8SLE";
    fixOneDriveBullshit(obsidianVault, removeTerm);

    System.out.println("...Finished!");
}

public static void fixOneDriveBullshit(ObsidianVault obsidianVault, String onedriveVestige) {
    // get all notes with vestige in title
    ArrayList<File> vestigialNotes = new ArrayList<File>();
    for (File note : obsidianVault.getNoteFiles()) {
        if (note.getName().contains(onedriveVestige)) {
            vestigialNotes.add(note);
        }
    }

    // for each of these notes, update name
    for (File noteToRename : vestigialNotes) {
        String newName = noteToRename.getName().replace(onedriveVestige,"").replace(".md","");
        System.out.println("Renaming note: "+noteToRename.getAbsolutePath()+"\n\tNew name: "+newName);
        obsidianVault.renameNote(noteToRename, newName);
    }
    System.out.println("Number of hits: "+vestigialNotes.size());
}

public static void replaceTextInFile(String filePath, String target, String replacement) {
    try {
        Path path = Paths.get(filePath);
        String content = new String(Files.readAllBytes(path));

        content = content.replace(target, replacement); // Replace occurrences

        Files.write(path, content.getBytes());
        System.out.println("Replacement completed successfully!\n\tRe-written file: "+filePath+"\n\tTarget term: "+target+"\n\tReplace term: "+replacement);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

