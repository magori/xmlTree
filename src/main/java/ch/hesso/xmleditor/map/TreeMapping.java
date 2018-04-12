package ch.hesso.xmleditor.map;

import ch.hesso.xmleditor.editdom.DomManipulater;
import ch.hesso.xmleditor.editdom.DomManipulation;

public class TreeMapping {
    DomManipulater domManipulater;
    String documentId;

    public TreeMapping() {
        this.domManipulater = new DomManipulation();
    }

    void createTree(String documentId) {
        this.documentId = documentId;
    }

    public Node createTree() {
        return this.domManipulater.createTree();
    }
}
