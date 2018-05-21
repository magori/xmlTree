package ch.hesso.xmleditor.display;

import ch.hesso.xmleditor.map.Mapper;
import ch.hesso.xmleditor.map.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

@SuppressWarnings({"ALL", "unchecked"})
public class VisuInteract {
    @Inject
    private Mapper mapper;

    private Node node;

    // GUI components
    private Stage primStage;
    private MenuBar menuBar;
    private MenuItem menuClose;

    private TreeTableView<Node> treeTable;
    private VBox root;

    private static void displayException(Thread t, Throwable ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("A exception append :(");
        alert.setContentText(ex.getMessage());

// Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public void createGUI(Stage stage, Menu menu) {
        Thread.setDefaultUncaughtExceptionHandler(VisuInteract::displayException);
        primStage = stage;
        menuClose = new MenuItem("Close");
        menu.getItems().add(menuClose);
        menuBar = new MenuBar();

        menuBar.getMenus().addAll(menu);

        addEvents();

        // Create a TreeTableView with model
        TreeItem<Node> rootNode = new TreeItem<Node>(node);
        treeTable = new TreeTableView<>(rootNode);
        treeTable.setPrefWidth(600);
        treeTable.setPrefHeight(500);

        // instantiate the root context menu
        ContextMenu rootContextMenu = ContextMenuBuilder.create().items(
                MenuItemBuilder.create()
                        .text("Add Child to Element")
                        .onAction(
                                new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent arg0) {
                                        askDataForNewElement();

                                    }
                                }
                        ).build()
        ).build();

        treeTable.setContextMenu(rootContextMenu);

        root = new VBox();
        root.getChildren().add(menuBar);
        root.getChildren().add(treeTable);

        // Create the Scene
        Scene scene = new Scene(root, 650, 500);
        // Add the Scene to the Stage
        primStage.setScene(scene);
        // Set the Title
        primStage.setTitle("Xml Editor");
        // Display the Stage
        primStage.show();
    }

    protected Stage getPrimStage() {
        return primStage;
    }

    /**
     * Load the nodes from the other layers
     *
     * @param idDocument: filepath of the document
     */
    protected void load(String idDocument) {
        this.node = this.mapper.createTree(idDocument);
    }

    /**
     * Create the visual representation of the xml file
     */
    protected void createTableTree() {

        TreeItem<Node> rootNode = createTree(node, new TreeItem(node));
        treeTable.setRoot(rootNode);

        // Create a TreeTableView with model
        TreeTableColumn<Node, String> nodeName = new TreeTableColumn<>(
                "Node Name");
        nodeName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        nodeName.setPrefWidth(250);
        treeTable.getColumns().add(nodeName);

        TreeTableColumn<Node, String> nodeText = new TreeTableColumn<>(
                "Node text");
        nodeText.setCellValueFactory(new TreeItemPropertyValueFactory<>("text"));
        nodeText.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        nodeText.setPrefWidth(300);
        nodeText.setOnEditCommit((
                TreeTableColumn.CellEditEvent<Node, String> event) -> {
            final Node item = event.getRowValue().getValue();
            System.out.println("Change Item " + item + " from "
                    + event.getOldValue() + " to new value "
                    + event.getNewValue());
            mapper.editNode(item.getId(), event.getNewValue());
            mapper.saveTree();
        });
        treeTable.getColumns().add(nodeText);

        TreeTableColumn<Node, String> nodeId = new TreeTableColumn<>("Node id");
        nodeId.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        nodeId.setPrefWidth(100);
        treeTable.getColumns().add(nodeId);

        treeTable.setShowRoot(false);
        treeTable.setEditable(true);
    }

    /**
     * Add event to the two menu buttons (load file and close file)
     */
    private void addEvents() {
        menuClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                TreeItem<Node> rootNode = new TreeItem(node);
                treeTable.setRoot(rootNode);
            }
        });
    }

    /**
     * Save the modification into the file
     */
    private void saveTree() {
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

    private void askDataForNewElement(){
        Dialog<String[]> dialog = new Dialog();
        dialog.setTitle("New Element to selected child");
        dialog.setHeaderText("Enter the node name and the node value of the new element.");
        dialog.setResizable(true);

        Label label1 = new Label("Name: ");
        Label label2 = new Label("Value: ");
        TextField text1 = new TextField();
        TextField text2 = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, String[]>() {
            @Override
            public String[] call(ButtonType b) {
                if (b == buttonTypeOk) {
                    return new String[]{text1.getText(), text2.getText() };
                }
                return null;
            }
        });

        Optional<String[]> result = dialog.showAndWait();
        if (result.isPresent()) {
            String[] newElementValue = result.get();
            if(newElementValue != null && newElementValue[0] != null && newElementValue[0].length() > 0 && newElementValue[1].length() > 0){
                String currentNodeID = treeTable.getSelectionModel().getSelectedItem().getValue().getId();
                TreeItem<Node> currentNode = treeTable.getSelectionModel().getSelectedItem();
                Node child = mapper.addNodeToParent(currentNodeID, newElementValue[0], newElementValue[1]);
                currentNode.getChildren().add(new TreeItem(child));
                mapper.saveTree();
            }
        }
    }
}