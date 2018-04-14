package ch.hesso.xmleditor.display;

import ch.hesso.xmleditor.map.Mapper;
import ch.hesso.xmleditor.map.Node;
import ch.hesso.xmleditor.map.TreeMapping;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private Mapper mapper = new TreeMapping();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Create a TreeTableView with model
        Node node = this.mapper.createTree("src/test/resources/exemple.xml");

        TreeItem<Node> rootNode = createTree(node, new TreeItem(node));

        TreeTableView<Node> treeTable = new TreeTableView<>(rootNode);
        treeTable.setPrefWidth(400);

        TreeTableColumn<Node, String> nodeName = new TreeTableColumn<>("Node Name");
        nodeName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        treeTable.getColumns().add(nodeName);

        TreeTableColumn<Node, String> nodeText = new TreeTableColumn<>("Node text");
        nodeText.setCellValueFactory(new TreeItemPropertyValueFactory<>("text"));
        treeTable.getColumns().add(nodeText);

        TreeTableColumn<Node, String> nodeId = new TreeTableColumn<>("Node id");
        nodeId.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        treeTable.getColumns().add(nodeId);


        // Create the VBox
        VBox root = new VBox(treeTable);

        // Set the Style-properties of the VBox
        root.setStyle("-fx-padding: 10;" +
                              "-fx-border-style: solid inside;" +
                              "-fx-border-width: 2;" +
                              "-fx-border-insets: 5;" +
                              "-fx-border-radius: 5;" +
                              "-fx-border-color: blue;");

        // Create the Scene
        Scene scene = new Scene(root);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title
        stage.setTitle("A simple TreeTableView");
        // Display the Stage
        stage.show();
    }

    private TreeItem createTree(Node current, TreeItem parent) {
        if (current.getChildren().isEmpty()) {
            return new TreeItem(current);
        } else {
            for (Node node: current.getChildren()) {
                TreeItem nodeChild = createTree(node, new TreeItem(node));
                parent.getChildren().add(nodeChild);
            }
        }
        return parent;
    }
}