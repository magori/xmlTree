package ch.hesso.xmleditor.editdom;

import ch.hesso.xmleditor.persistence.FileManager;
import org.assertj.core.api.Assertions;
import org.jdom2.Document;
import org.junit.jupiter.api.Test;

class DomManipulationTest {


    private DomManipulation domManipulation = new DomManipulation("src/test/resources/exemple.xml", new FileManager());

    @Test
    void parse() {
        Document document = domManipulation.parse("<root><node1>nodeText</node1></root>");
        Assertions.assertThat(document.getRootElement().getChildren().get(0).getText()).isEqualTo("nodeText");
    }

    @Test
    void findElement() {
        Assertions.assertThat(domManipulation.findElement("1-3").getText()).isEqualTo("200000");
    }

    @Test
    void editElement() {
        Assertions.assertThat(domManipulation.editElement("1-3", "3000").getText()).isEqualTo("3000");
    }

    @Test
    void createTree() {
        domManipulation.createTree();
    }
}