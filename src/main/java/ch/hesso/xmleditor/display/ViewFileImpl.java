package ch.hesso.xmleditor.display;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

import java.io.File;

public class ViewFileImpl extends VisuInteract implements View {
    private MenuItem menuLoad;

    @Override
    public void menuLaoder(Menu menuRoot) {
        menuLoad = new MenuItem("Load file");
        menuRoot.getItems().addAll(menuLoad);
        this.addEvents();
    }

    /**
     * Add event to the two menu buttons (load file and close file)
     */
    private void addEvents() {

        menuLoad.setOnAction(arg0 -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open XML file");

            // Set user working directory as default directory
            String workingDir = System.getProperty("user.dir");
            File defaultDirectory = new File(workingDir);
            fileChooser.setInitialDirectory(defaultDirectory);

            File file = fileChooser.showOpenDialog(getPrimStage());
            if (file != null) {
                load(file.getAbsolutePath());
                createTableTree();
            }
        });

    }
}
