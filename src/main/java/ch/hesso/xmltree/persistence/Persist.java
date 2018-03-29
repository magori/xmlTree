package ch.hesso.xmltree.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Persist {
    public static void save(String fileName, String content) {
        Path newFilePath = Paths.get(fileName);
        try {
            Files.write(newFilePath, content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Unable to write file: "+fileName,e);
        }
    }
}
