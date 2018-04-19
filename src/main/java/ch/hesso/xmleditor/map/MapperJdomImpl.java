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
    public NodeImpl createTree(String idDocument) {
        this.domManipulater.load(idDocument);
        NodeImpl node = new NodeImpl(null, domManipulater.getRootElement().getName(), null);
        return this.createTree(this.domManipulater.getRootElement(), null, node);
    }

    private NodeImpl createTree(Element element, String id, NodeImpl parent) {
        if (element.getChildren().isEmpty()) {
            return new NodeImpl(id, element.getName(), element.getText());
        } else {
            for (int i = 0; i < element.getChildren().size(); i++) {
                String newId = id + "-" + i;
                if (id == null) {
                    newId = i + "";
                }
                NodeImpl nodeParent = new NodeImpl(newId, element.getChildren().get(i).getName(), null);
                NodeImpl nodeChild = createTree(element.getChildren().get(i), newId, nodeParent);
                parent.getChildren().add(nodeChild);
            }
        }
        return parent;
    }
}
