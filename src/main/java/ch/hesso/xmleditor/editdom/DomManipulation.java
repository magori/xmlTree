package ch.hesso.xmleditor.editdom;

import ch.hesso.xmleditor.persistence.FileManager;
import ch.hesso.xmleditor.persistence.Persister;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DomManipulation implements DomManipulater {

    private final Persister persister;
    private Document document;
    private String idDocument;

    public DomManipulation() {
        this.persister = new FileManager();
    }

    public void load(String idDocument) {
        this.idDocument = idDocument;
        document = this.parse(this.persister.load(idDocument));
    }

    @Override
    public Element getRootElement() {
        return this.document.getRootElement();
    }

    public Element editElement(String id, String newText) {
        Element element = this.findElement(id);
        element.setText(newText);
        return element;
    }

    public void saveDocument() {
        this.persister.save(this.idDocument, new XMLOutputter().outputString(document));
    }

    Document parse(String content) {
        SAXBuilder builder = new SAXBuilder();
        try {
            return builder.build(new StringReader(content));
        } catch (JDOMException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    Element findElement(String nodeId) {
        List<Integer> ids = Arrays.stream(nodeId.split("-"))
                                  .map(Integer::valueOf)
                                  .collect(Collectors.toList());

        List<Element> elements = document.getRootElement().getChildren();
        for (int i = 0; i < (ids.size() - 1); i++) {
            elements = elements.get(ids.get(i)).getChildren();
        }
        return elements.get(ids.get(ids.size() - 1));
    }
}
