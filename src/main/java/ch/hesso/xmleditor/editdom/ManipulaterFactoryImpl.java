package ch.hesso.xmleditor.editdom;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;

@Singleton
public class ManipulaterFactoryImpl implements ManipulaterFactory {
    private Map<ManipulaterType, Manipulater> manipulaters;

    @Inject
    public ManipulaterFactoryImpl(Map<ManipulaterType, Manipulater> manipulaters) {
        this.manipulaters = manipulaters;
    }

    @Override
    public Manipulater getManipulater(ManipulaterType manipulaterType) {
        return this.manipulaters.get(manipulaterType);
    }
}
