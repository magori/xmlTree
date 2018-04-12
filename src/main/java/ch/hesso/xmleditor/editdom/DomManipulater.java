package ch.hesso.xmleditor.editdom;

import ch.hesso.xmleditor.map.Node;

public interface DomManipulater {
   //Elment editElement(String id, String newText);

    void saveDocument();

    Node createTree();
}
