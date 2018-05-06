package ch.hesso.xmleditor.editdom;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class ManipulaterFactoryImpl implements ManipulaterFactory {
    private final Map<String, Manipulater> manipulaters;

    @Inject
    public ManipulaterFactoryImpl(Map<String, Manipulater> manipulaters) {
        this.manipulaters = manipulaters;
    }

    @Override
    public Manipulater getManipulater(String manipulaterType) {
        Manipulater manipulater = this.manipulaters.get(manipulaterType);
        if (manipulater == null) {
            throw new RuntimeException("No manipulater is implemented for the type:  '" + manipulaterType + "'");
        }
        return manipulater;
    }
}
