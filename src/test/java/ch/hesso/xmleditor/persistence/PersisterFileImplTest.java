package ch.hesso.xmleditor.persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class PersisterFileImplTest {
    private final String fileName = "src/test/resources/checkWrite.xml";
    private final PersisterFileImpl persisterFileImpl = new PersisterFileImpl();

    @Test
    void saveNewFile() throws IOException {
        Path path = (Paths.get(fileName));
        String content = "<?xml version=\"1.0\"?>\n" +
                "<company>\n" +
                "    <staff id=\"1001\">\n" +
                "        <firstname>yong</firstname>\n" +
                "        <lastname>mook kim</lastname>\n" +
                "        <nickname>mkyong</nickname>\n" +
                "        <salary>100000</salary>\n" +
                "    </staff>\n" +
                "</company>";
        persisterFileImpl.save(fileName, content);
        Assertions.assertThat(new String(Files.readAllBytes(path))).isEqualTo(content);
        Files.delete(path);
    }

    @Test
    void saveOldFile() throws IOException {
        Path path = (Paths.get(fileName));
        persisterFileImpl.save(fileName, "test");
        persisterFileImpl.save(fileName, "test2");
        Assertions.assertThat(new String(Files.readAllBytes(path))).isEqualTo("test2");
        Files.delete(path);
    }

    @Test
    void loadFile() throws IOException {
        persisterFileImpl.save(fileName, "test");
        Assertions.assertThat(persisterFileImpl.load(fileName)).isEqualTo("test");
        Path path = (Paths.get(fileName));
        Files.delete(path);
    }

}