package ch.hesso.xmleditor.display;

import ch.hesso.xmleditor.editdom.Manipulater;
import ch.hesso.xmleditor.editdom.ManipulaterJsonImpl;
import ch.hesso.xmleditor.map.Mapper;
import ch.hesso.xmleditor.map.MapperImpl;
import ch.hesso.xmleditor.persistence.Persister;
import ch.hesso.xmleditor.persistence.PersisterFileImpl;
import com.google.inject.AbstractModule;

class XmlEditorManagerModuleGuice extends AbstractModule {
    @Override
    protected void configure() {
        bind(Mapper.class).to(MapperImpl.class);
        bind(Manipulater.class).to(ManipulaterJsonImpl.class);
        bind(Persister.class).to(PersisterFileImpl.class);
    }
}
