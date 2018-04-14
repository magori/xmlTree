package ch.hesso.xmleditor.display;

import ch.hesso.xmleditor.map.Mapper;
import ch.hesso.xmleditor.map.Node;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.inject.Inject;

@SuppressWarnings({"ALL", "unchecked"})
class VisuInteract {
    @Inject
    private Mapper mapper;
    private Node node;

    public void load(String idDocument) {
        this.node = this.mapper.createTree(idDocument);
    }

    public void createTableTree(Stage stage) {
        // Create a TreeTableView with model
        TreeItem<Node> rootNode = createTree(node, new TreeItem(node));

        TreeTableView<Node> treeTable = new TreeTableView<>(rootNode);
        treeTable.setPrefWidth(600);

        TreeTableColumn<Node, String> nodeName = new TreeTableColumn<>("Node Name");
        nodeName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        nodeName.setPrefWidth(250);
        treeTable.getColumns().add(nodeName);

        TreeTableColumn<Node, String> nodeText = new TreeTableColumn<>("Node text");
        nodeText.setCellValueFactory(new TreeItemPropertyValueFactory<>("text"));
        nodeText.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        nodeText.setPrefWidth(300);
        nodeText.setOnEditCommit((TreeTableColumn.CellEditEvent<Node, String> event) -> {
            final Node item = event.getRowValue().getValue();
            System.out.println("Change Item " + item + " from " + event.getOldValue() + " to new value " + event.getNewValue());
            mapper.editNode(item.getId(), event.getNewValue());
            mapper.saveTree();
        });
        treeTable.getColumns().add(nodeText);

        TreeTableColumn<Node, String> nodeId = new TreeTableColumn<>("Node id");
        nodeId.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        nodeId.setPrefWidth(50);
        treeTable.getColumns().add(nodeId);

        treeTable.setShowRoot(false);
        treeTable.setEditable(true);

        // Create the VBox
        VBox root = new VBox(treeTable);

        // Create the Scene
        Scene scene = new Scene(root);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title
        stage.setTitle("Xml Editor");
        // Display the Stage
        stage.show();
    }

    public void saveTree() {
        this.mapper.saveTree();
    }

    private TreeItem createTree(Node current, TreeItem parent) {
        if (current.getChildren().isEmpty()) {
            return new TreeItem(current);
        } else {
            for (Node node : current.getChildren()) {
                TreeItem nodeChild = createTree(node, new TreeItem(node));
                parent.getChildren().add(nodeChild);
            }
        }
        return parent;
    }
}
