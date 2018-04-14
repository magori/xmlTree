package ch.hesso.xmleditor.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class PersisterFileImpl implements Persister {
    public void save(String fileName, String content) {
        Path newFilePath = Paths.get(fileName);
        try {
            Files.write(newFilePath, content.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Unable to write file: " + fileName, e);
        }
    }

    public String load(String fileName) {
        Path path = Paths.get(fileName);
        try {
            return Files.lines(path).collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException("Unable to load file: " + fileName, e);
        }
    }
}
