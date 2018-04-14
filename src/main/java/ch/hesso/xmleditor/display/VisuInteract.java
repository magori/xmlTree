package ch.hesso.xmleditor.display;

import ch.hesso.xmleditor.map.Mapper;
import ch.hesso.xmleditor.map.Node;
import ch.hesso.xmleditor.map.TreeMapping;

class VisuInteract {
    private final Mapper mapper = new TreeMapping();
    private final Node node;

    public VisuInteract(String idDocument) {
        this.node = this.mapper.createTree(idDocument);
    }

    public void editNode(String idDocument, String newText) {
        this.mapper.editNode(idDocument, newText);
    }

    public void saveTree() {
        this.mapper.saveTree();
    }

    public Node getNode() {
        return node;
    }
}
