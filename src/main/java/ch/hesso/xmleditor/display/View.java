package ch.hesso.xmleditor.display;

import javafx.scene.control.Menu;
import javafx.stage.Stage;

public interface View {
    void menuLaoder(Menu menuRoot);

    void createGUI(Stage stage, Menu menu);
}
