package ch.hesso.xmltree.persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class PersistTest {

    @Test
    void persit() throws IOException {
        String fileName = "src/test/resources/checkWrite.xml";
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
        Persist.save(fileName, content);
        Assertions.assertThat(new String(Files.readAllBytes(path))).isEqualTo(content);
        Files.delete(path);
    }

}