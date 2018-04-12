package ch.hesso.xmleditor.map;

import ch.hesso.xmleditor.editdom.DomManipulater;

public class TreeMapping implements DomManipulater {
    DomManipulater domManipulater;
    String documentId;

    public TreeMapping(DomManipulater domManipulater) {
        this.domManipulater = domManipulater;
    }

    void createTree(String documentId) {
        this.documentId = documentId;

    }


}
