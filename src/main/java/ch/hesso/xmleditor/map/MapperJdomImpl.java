package ch.hesso.xmleditor.map;

import ch.hesso.xmleditor.editdom.DomManipulater;
import ch.hesso.xmleditor.editdom.Element;

import javax.inject.Inject;


public class MapperJdomImpl implements Mapper {
    private final DomManipulater domManipulater;

    @Inject
    public MapperJdomImpl(DomManipulater domManipulater) {
        this.domManipulater = domManipulater;
    }

    @Override
    public void editNode(String id, String newText) {
        this.domManipulater.editElement(id, newText);
    }

    @Override
    public void saveTree() {
        this.domManipulater.saveDocument();
    }

    @Override
    public Node createTree(String idDocument) {
        this.domManipulater.load(idDocument);
        Node node = new Node(null, domManipulater.getRootElement().getName(), null);
        return this.createTree(this.domManipulater.getRootElement(), null, node);
    }

    private Node createTree(Element element, String id, Node parent) {
        if (element.getChildren().isEmpty()) {
            return new Node(id, element.getName(), element.getText());
        } else {
            for (int i = 0; i < element.getChildren().size(); i++) {
                String newId = id + "-" + i;
                if (id == null) {
                    newId = i + "";
                }
                Node nodeParent = new Node(newId, element.getChildren().get(i).getName(), null);
                Node nodeChild = createTree(element.getChildren().get(i), newId, nodeParent);
                parent.getChildren().add(nodeChild);
            }
        }
        return parent;
    }
}
