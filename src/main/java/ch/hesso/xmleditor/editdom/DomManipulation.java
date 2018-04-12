package ch.hesso.xmleditor.editdom;

import ch.hesso.xmleditor.map.Node;
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

public class DomManipulation {

    private final Document document;
    private final Persister persister;
    private final String idDocument;

    DomManipulation(String idDocument, Persister persister) {
        this.persister = persister;
        this.idDocument = idDocument;
        document = this.parse(persister.load(idDocument));
    }

    public Element editElement(String id, String newText) {
        Element element = this.findElement(id);
        element.setText(newText);
        return element;
    }

    public void saveDocument() {
        this.persister.save(this.idDocument, new XMLOutputter().outputString(document));
    }

    public Node createTree() {
        Node node = new Node("1", this.document.getRootElement().getName(), null);
        return this.createTree(this.document.getRootElement(), "1", node);
    }

    public Node createTree(Element element, String id, Node parent) {
        if (element.getChildren().isEmpty()) {
            return new Node(id, element.getName(), element.getText());
        } else {
            for (int i = 0; i < element.getChildren().size(); i++) {
                Node nodeChild = createTree(element.getChildren().get(i), id + "-" + i, new Node(id + "-" +i, element.getChildren().get(i).getName
                        (), null));
                parent.getChildren().add(nodeChild);
            }
        }
        return parent;
    }

    Document parse(String content) {
        SAXBuilder builder = new SAXBuilder();
        try {
            return builder.build(new StringReader(content));
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
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
