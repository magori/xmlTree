package ch.hesso.xmleditor.editdom;

import com.google.inject.Injector;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

class ManipulaterProviderOld implements Provider<Manipulater> {

    @Inject
    private Injector injector;
    private Manipulater manipulaterJson;
    private Manipulater manipulaterXml;
    private ManipulaterType mapperType;

    @Inject
    public ManipulaterProviderOld(@Named("json") Manipulater manipulaterJson, @Named("xml") Manipulater manipulaterXml) {
        this.manipulaterJson = manipulaterJson;
        this.manipulaterXml = manipulaterXml;
    }

    public ManipulaterProviderOld setType(ManipulaterType type) {
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
