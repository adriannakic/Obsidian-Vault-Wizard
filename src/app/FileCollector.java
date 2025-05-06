package app;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class FileCollector {
    private File rootFolder = null;
    private ArrayList<File> fileList;

    public FileCollector (String folderPath) {
        rootFolder = new File(folderPath);

        clearFileList();
        collectFiles(rootFolder, fileList);

        // Print collected file paths
        //fileList.forEach(file -> System.out.println(file.getAbsolutePath()));
    }

    public void clearFileList () {
        fileList = new ArrayList<File>();
    }

    public void collectFiles(File folder, List<File> fileList) {
        long startTime = System.nanoTime();
        if (folder == null || !folder.exists()) return;

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    collectFiles(file, fileList); // Recursive call for subdirectories
                } else if (file.getName().toLowerCase().endsWith(".md")) {
                    boolean duplicateFile = false;
                    for (File addedFile : fileList) {
                        if (addedFile.getName().equals(file.getName())) {
                            System.out.println("Duplicate files found:\n(existing): "+addedFile.getAbsolutePath()+"\n(duplicate): "+file.getAbsolutePath());
                            duplicateFile = true;
                            break;
                        }
                    }
                    if (!duplicateFile) {
                        fileList.add(file); // Add file to list
                    }
                }
            }
        }
        long endTime = System.nanoTime(); // End timing
        long duration = (endTime - startTime) / 1_000_000; // Convert to millisec
        System.out.println("Collecting folder: ("+folder.getAbsolutePath());
        System.out.println("\tTime:\t\t"+duration+"ms");
        System.out.println("\tFile Count:\t\t"+fileList.size());
    }

    public ArrayList<File> getFileList () {
        return fileList;
    }

    public String toString () {
        StringBuffer output = new StringBuffer("");

        output.append("FileCollector Summary:");
        output.append("\n\t.md file count:\t\t"+fileList.size());
        output.append("\n\tvault root folder:\t\t"+rootFolder.getAbsolutePath());

        return output.toString();
    }
}