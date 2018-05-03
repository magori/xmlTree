package ch.hesso.xmleditor.editdom;

import ch.hesso.xmleditor.persistence.Persister;
import ch.hesso.xmleditor.persistence.PersisterFileImpl;
import org.assertj.core.api.Assertions;
import org.jdom2.Document;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class ManipulaterJdomImplTest {
    private static final Persister persister = Mockito.mock(Persister.class);
    private final ManipulaterJdomImpl domManipulation = new ManipulaterJdomImpl(new PersisterFileImpl());
    private final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<company>" +
            "  <staff id=\"2001\">" +
            "    <firstname>low</firstname>" +
            "    <lastname>yin fong</lastname>" +
            "  </staff>" +
            "</company>";

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

    @Test
    void saveDocument() {
        ManipulaterJdomImpl domManipulation = new ManipulaterJdomImpl(persister);
        ArgumentCaptor<String> captur = ArgumentCaptor.forClass(String.class);
        Mockito.when(persister.load("")).thenReturn(xml);
        domManipulation.load("");
        domManipulation.saveDocument();
        Mockito.verify(persister).save(captur.capture(), captur.capture());
        Assertions.assertThat(captur.getAllValues().get(1)).isEqualToIgnoringWhitespace(xml);
    }

}