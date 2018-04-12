package ch.hesso.xmleditor.map;

import ch.hesso.xmleditor.editdom.DomManipulater;
import ch.hesso.xmleditor.editdom.DomManipulation;
import org.jdom2.Element;

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
    public Node createTree(String id) {
        this.domManipulater.load(id);
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
