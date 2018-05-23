package ch.hesso.xmleditor.display;

import javafx.scene.control.Menu;
import javafx.stage.Stage;

import javax.inject.Inject;

public class MainView {
    @Inject
    private View view;

    void createGUI(Stage stage) {
        Menu menu = new Menu("Files");
        view.menuLaoder(menu);
        view.createGUI(stage, menu);
    }
}
