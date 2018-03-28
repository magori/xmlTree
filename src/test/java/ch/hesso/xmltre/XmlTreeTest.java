package ch.hesso.xmltre;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.jupiter.api.Test;

class XmlTreeTest {

    @Test
    void parseFile() throws Exception {
        Document document = XmlTree.read("src\\test\\resources\\exemple.xml");
        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());

        sortie.output(document.getRootElement().getChildren().get(0).setName("DHDH"), System.out);
    }

    @Test
    void parseString() {
    }
}