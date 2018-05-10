package ch.hesso.xmleditor.display;

import ch.hesso.xmleditor.map.Mapper;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ViewDbImpl extends VisuInteract implements View {
    private List<MenuItem> dbFiles;
    @Inject
    private Mapper mapper;

    @Override
    public void menuLaoder(Menu menuRoot) {
        this.dbFiles = new ArrayList<>();
        for (String elem : mapper.fetchDbList()) {
            MenuItem mi = new MenuItem(elem);
            this.dbFiles.add(mi);
            menuRoot.getItems().add(mi);
        }
        this.addEvents();
    }

    /**
     * Add event to the two menu buttons (load file and close file)
     */
    private void addEvents() {
        for (MenuItem mi : this.dbFiles) {
            mi.setOnAction(event -> {
                load(mi.getText().split("__")[1]);
                createTableTree();
            });
        }
    }
}

