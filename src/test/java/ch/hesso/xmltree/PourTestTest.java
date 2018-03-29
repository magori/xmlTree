package ch.hesso.xmltree;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

class PourTestTest {

    @Test
    void parseFile() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();

        String fileName = "src/test/resources/exemple.xml";
        Path path = (Paths.get(fileName));
        Document document = PourTest.read(path.toFile());
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());

        sortie.output(document.getRootElement().getChildren().get(0).setName("DHDH"), System.out);
    }

    @Test
    void parseString() {
    }
}