package ch.hesso.xmleditor.display;

import ch.hesso.xmleditor.editdom.*;
import ch.hesso.xmleditor.map.Mapper;
import ch.hesso.xmleditor.map.MapperImpl;
import ch.hesso.xmleditor.persistence.Persister;
import ch.hesso.xmleditor.persistence.PersisterDbImpl;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

class XmlEditorManagerModuleGuice extends AbstractModule {
    @Override
    protected void configure() {
        bind(Mapper.class).to(MapperImpl.class);
        bind(Manipulater.class).annotatedWith(Names.named(ManipulaterType.JSON.getName())).to(ManipulaterJsonImpl.class);
        bind(Manipulater.class).annotatedWith(Names.named(ManipulaterType.XML.getName())).to(ManipulaterJdomImpl.class);
        bind(Manipulater.class).toProvider(ManipulaterProvider.class);
        bind(Persister.class).to(PersisterDbImpl.class);
    }
}
