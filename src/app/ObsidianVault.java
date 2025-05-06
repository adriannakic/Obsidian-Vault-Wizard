package app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class ObsidianVault {
    private ArrayList<File> markdownFiles;
    private File rootFolder;

    public ObsidianVault () {
        markdownFiles = new ArrayList<File>();
        rootFolder = null;
    }

    public void setMarkdownFiles(ArrayList<File> markdownFiles) {
        this.markdownFiles = markdownFiles;
    }

    public void setRootFolder(File rootFolder) {
        this.rootFolder = rootFolder;
    }

//    public void renameNote(File note, String newName) {
//        Path oldPath = Paths.get(note.getName());
//        Path newPath = Paths.get(newName);
//
//        try {
//            Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
//            System.out.println("File renamed successfully!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public File getNoteFile(String filename) {
        for (File file : markdownFiles) {
            if (file.getName().equals(filename)) {
                System.out.println("Found file:\n\tSearch: "+filename+"\n\tLocated: "+file.getAbsolutePath());
                return file;
            }
        }
        System.out.println("Couldn't find file:\n\tSearch: "+filename);
        return null;
    }

    public ArrayList<File> getNoteFiles() {
        return markdownFiles;
    }

    public static boolean containsString(String filePath, String searchString) {
        try {
            Path path = Paths.get(filePath);
            String content = new String(Files.readAllBytes(path));

            return content.contains(searchString); // Check if the string exists
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public ArrayList<File> getMarkdownFiles(String searchterm) {
        ArrayList<File> matchedMarkdownFiles = new ArrayList<File>();
        for (File markdownfile : markdownFiles) {
            if (containsString(markdownfile.getAbsolutePath(),searchterm)) {
                System.out.println("Search Term Hit!\n\tSearch term: "+searchterm+"\n\tContained within: "+markdownfile.getAbsolutePath());
                matchedMarkdownFiles.add(markdownfile);
            }
        }
        return matchedMarkdownFiles;
    }

    public static void replaceTextInNote(String filePath, String target, String replacement) {
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

    public void renameNote (File note, String newname) {
        // renaming file
        String filename = note.getName();                                                       // file to rename
        String notename = filename.replace(".md","");                                        // filename without .md

        if (note != null) {
            String newFilename = newname;
            Path oldPath = Paths.get(note.getAbsolutePath());
            Path newPath = Paths.get(note.getAbsolutePath().replace(notename,newname));
            try {
                Files.move(oldPath, newPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File renamed successfully:\n\tOld path: "+oldPath+"\n\tNew path: "+newPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // update internal links across vault
            ArrayList<File> backlinkedNotes = this.getMarkdownFiles(notename);
            for (File markdownNote : backlinkedNotes) {
                // for each note, replace any text of [old file name] with [new file name]
                replaceTextInNote(markdownNote.getAbsolutePath(),notename,newname);
            }
        }
    }
}
