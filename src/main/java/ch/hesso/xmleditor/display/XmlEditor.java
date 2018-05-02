package ch.hesso.xmleditor.display;

import ch.hesso.xmleditor.editdom.Manipulater;
import com.google.inject.Guice;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.stage.Stage;

public class XmlEditor extends Application{

	
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
	public void start(Stage stage) {
    	final Injector injector = Guice.createInjector(new XmlEditorManagerModuleGuice());
		injector.getProvider(Manipulater.class);
    	final VisuInteract visuInteract = injector.getInstance(VisuInteract.class);
    	visuInteract.createGUI(stage);
	}

}