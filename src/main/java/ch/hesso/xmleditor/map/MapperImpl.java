package ch.hesso.xmleditor.map;

import ch.hesso.xmleditor.editdom.Manipulater;
import ch.hesso.xmleditor.editdom.Element;

import javax.inject.Inject;


public class MapperImpl implements Mapper {
    private final Manipulater manipulater;

    @Inject
    public MapperImpl(Manipulater manipulater) {
        this.manipulater = manipulater;
    }

    @Override
    public void editNode(String id, String newText) {
        this.manipulater.editElement(id, newText);
    }

    @Override
    public void saveTree() {
        this.manipulater.saveDocument();
    }

    @Override
    public NodeImpl createTree(String idDocument) {
        this.manipulater.load(idDocument);
        NodeImpl node = new NodeImpl(null, manipulater.getRootElement().getName(), null);
        return this.createTree(this.manipulater.getRootElement(), null, node);
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
