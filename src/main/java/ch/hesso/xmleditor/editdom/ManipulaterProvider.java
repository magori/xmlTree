package ch.hesso.xmleditor.editdom;

import com.google.inject.Injector;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

public class ManipulaterProvider implements Provider<Manipulater> {

    @Inject
    private Injector injector;
    @Inject
    @Named("json")
    private Manipulater manipulaterJson;
    @Inject
    @Named("xml")
    private Manipulater manipulaterXml;

    private ManipulaterType mapperType;

    public ManipulaterProvider setType(ManipulaterType type) {
        this.mapperType = type;
        return this;
    }

    @Override
    public Manipulater get() {
        switch (mapperType) {
            case XML:
                return manipulaterXml; //injector.getInstance(ManipulaterJdomImpl.class);
            case JSON:
                return manipulaterJson; //injector.getInstance(ManipulaterJsonImpl.class);
            default:
                throw new RuntimeException("This type " + mapperType + "is not managed!");
        }
    }
}
