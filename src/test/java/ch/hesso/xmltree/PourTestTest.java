package ch.hesso.xmltree;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.jupiter.api.Test;

import java.io.File;

class PourTestTest {

    @Test
    void parseFile() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("exemple.xml").getFile());
        Document document = PourTest.read(file);
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());

        sortie.output(document.getRootElement().getChildren().get(0).setName("DHDH"), System.out);
    }

    @Test
    void parseString() {
    }
}