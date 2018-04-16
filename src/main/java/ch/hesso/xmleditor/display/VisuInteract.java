package ch.hesso.xmleditor.display;

import java.io.File;

import ch.hesso.xmleditor.map.Mapper;
import ch.hesso.xmleditor.map.Node;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.inject.Inject;

import com.google.inject.Guice;
import com.google.inject.Injector;

@SuppressWarnings({ "ALL", "unchecked" })
public class VisuInteract {
	@Inject
	private Mapper mapper;
	private Node node;

	// GUI components
	private Stage primStage;
	private MenuBar menuBar;
	private MenuItem menuLoad;
	private MenuItem menuClose;
	private TreeTableView<Node> treeTable;
	private VBox root;

	public void createGUI(Stage stage) {

		primStage = stage;
		menuBar = new MenuBar();
		Menu menu = new Menu("File");
		menuLoad = new MenuItem("Load");
		menuClose = new MenuItem("Close");

		addEvents();

		menu.getItems().addAll(menuLoad, menuClose);
		menuBar.getMenus().addAll(menu);

		// Create a TreeTableView with model
		TreeItem<Node> rootNode = new TreeItem<Node>(node);
		treeTable = new TreeTableView<>(rootNode);
		treeTable.setPrefWidth(600);

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
	
	/**
	 * Add event to the two menu buttons (load file and close file)
	 */
	private void addEvents() {

		menuLoad.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open XML file");

				// Set user working directory as default directory
				String workingDir = System.getProperty("user.dir");
				File defaultDirectory = new File(workingDir);
				fileChooser.setInitialDirectory(defaultDirectory);

				File file = fileChooser.showOpenDialog(primStage);
				if (file != null) {
					load(file.getAbsolutePath());
					createTableTree();
				}
			}
		});

		menuClose.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				TreeItem<Node> rootNode = new TreeItem(node);
				treeTable.setRoot(rootNode);
			}
		});
	}

	public void load(String idDocument) {
		this.node = this.mapper.createTree(idDocument);
	}

	public void createTableTree() {

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
