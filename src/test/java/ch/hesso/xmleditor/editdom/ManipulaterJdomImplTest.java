package ch.hesso.xmleditor.editdom;

import ch.hesso.xmleditor.persistence.PersisterFileImpl;
import org.assertj.core.api.Assertions;
import org.jdom2.Document;
import org.junit.jupiter.api.Test;

class ManipulaterJdomImplTest {
    private final ManipulaterJdomImpl domManipulation = new ManipulaterJdomImpl(new PersisterFileImpl());

    @Test
    void parse() {
        this.domManipulation.load("src/test/resources/exemple.xml");
        Document document = domManipulation.parse("<root><node1>nodeText</node1></root>");
        Assertions.assertThat(document.getRootElement().getChildren().get(0).getText()).isEqualTo("nodeText");
    }

    @Test
    void findElement() {
        this.domManipulation.load("src/test/resources/exemple.xml");
        Assertions.assertThat(domManipulation.findElement("1-3").getText()).isEqualTo("200000");
    }

    @Test
    void editElement() {
        this.domManipulation.load("src/test/resources/exemple.xml");
        Assertions.assertThat(domManipulation.editElement("1-3", "3000").getText()).isEqualTo("3000");
    }
}