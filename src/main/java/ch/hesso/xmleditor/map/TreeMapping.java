package ch.hesso.xmleditor.map;

import ch.hesso.xmleditor.editdom.DomManipulater;
import ch.hesso.xmleditor.editdom.DomManipulation;

public class TreeMapping implements Mapper {
    DomManipulater domManipulater = new DomManipulation();

    @Override
    public void editNode(String id, String newText) {
        this.domManipulater.editElement(id, newText);
    }
    @Override
    public void saveTree() {
        this.domManipulater.saveDocument();
    }

    @Override
    public Node createTree(String documentId) {
        this.domManipulater.load(documentId);
        return this.domManipulater.createTree();
    }
}
