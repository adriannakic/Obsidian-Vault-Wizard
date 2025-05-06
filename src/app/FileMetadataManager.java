//package app;
//
//import org.json.JSONObject;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//public class FileMetadataManager {
//    public static void main(String[] args) {
//        JSONObject fileData = new JSONObject();
//        fileData.put("fileName", "example.txt");
//        fileData.put("filePath", Paths.get("src/resources/example.txt").toString());
//        fileData.put("lastUsed", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
//
//        try (FileWriter file = new FileWriter("src/resources/file_metadata.json")) {
//            file.write(fileData.toString(4)); // Pretty print JSON
//            System.out.println("Metadata saved successfully!");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}