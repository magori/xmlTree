package ch.hesso.xmleditor.editdom;

import ch.hesso.xmleditor.persistence.Persister;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.inject.Inject;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ManipulaterJdomImpl implements Manipulater {

    private Persister persister;
    private Document document;
    private String idDocument;

    //Obligatoir pour l'introspection, afin de determiner le type
    public ManipulaterJdomImpl() {}

    @Inject
    public ManipulaterJdomImpl(Persister persister) {
        this.persister = persister;
    }

    public void load(String idDocument) {
        this.idDocument = idDocument;
        document = this.parse(this.persister.load(idDocument));
    }

    @Override
    public List<String> fetchDbList() {
        return persister.fetchDbList();
    }

    @Override
    public String forType() {
        return ManipulaterType.XML.getName();
    }

    @Override
    public Element getRootElement() {
        return new ElementJdomImpl(this.document.getRootElement());
    }

    @Override
    public Element editElement(String id, String newText) {
        org.jdom2.Element element = this.findElement(id);
        element.setText(newText);
        return new ElementJdomImpl(element);
    }


    public String addElementToParent(String parentId, String name, String text){
        org.jdom2.Element parent = this.findElement(parentId);

        int newChildID = parent.getChildren().size();
        org.jdom2.Element newElement = new org.jdom2.Element(name);
        newElement.setText(text);
        parent.getChildren().add(newElement);
        return parentId + "-" + newChildID;

    }

    public void saveDocument() {
        this.persister.save(this.idDocument, new XMLOutputter(Format.getPrettyFormat()).outputString(document));
    }

    Document parse(String content) {
        SAXBuilder builder = new SAXBuilder();
        try {
            return builder.build(new StringReader(content));
        } catch (JDOMException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    org.jdom2.Element findElement(String nodeId) {
        List<Integer> ids = Arrays.stream(nodeId.split("-"))
                                  .map(Integer::valueOf)
                                  .collect(Collectors.toList());

        List<org.jdom2.Element> elements = document.getRootElement().getChildren();
        for (int i = 0; i < (ids.size() - 1); i++) {
            elements = elements.get(ids.get(i)).getChildren();
        }
        return elements.get(ids.get(ids.size() - 1));
    }
}
