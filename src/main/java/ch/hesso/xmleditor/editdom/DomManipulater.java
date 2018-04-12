package ch.hesso.xmleditor.editdom;

import ch.hesso.xmleditor.map.Node;
import org.jdom2.Element;

public interface DomManipulater {
    Element editElement(String id, String newText);

    void saveDocument();

    Node createTree();

     void load(String idDocument);
}
