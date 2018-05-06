package ch.hesso.xmleditor.display;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlEditor extends Application {
    private static final Logger LOG = LoggerFactory.getLogger(XmlEditor.class);


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            final Injector injector = Guice.createInjector(new XmlEditorManagerModuleGuice());
            final VisuInteract visuInteract = injector.getInstance(VisuInteract.class);
            visuInteract.createGUI(stage);
        } catch (Exception e) {
            LOG.error("Error: {}", e);
            System.exit(0);
        }
    }

}