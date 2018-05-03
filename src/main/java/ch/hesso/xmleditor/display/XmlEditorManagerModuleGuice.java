package ch.hesso.xmleditor.display;

import ch.hesso.xmleditor.editdom.*;
import ch.hesso.xmleditor.map.Mapper;
import ch.hesso.xmleditor.map.MapperImpl;
import ch.hesso.xmleditor.persistence.Persister;
import ch.hesso.xmleditor.persistence.PersisterDbImpl;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import org.jooq.SQLDialect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class XmlEditorManagerModuleGuice extends AbstractModule {


    @Override
    protected void configure() {

        MapBinder<ManipulaterType, Manipulater> mapbinder = MapBinder.newMapBinder(binder(), ManipulaterType.class, Manipulater.class);
        mapbinder.addBinding(ManipulaterType.JSON).to(ManipulaterJsonImpl.class);
        mapbinder.addBinding(ManipulaterType.XML).to(ManipulaterJdomImpl.class);

        bind(ManipulaterFactory.class).to(ManipulaterFactoryImpl.class);
        bind(Mapper.class).to(MapperImpl.class);

        bind(Persister.class).to(PersisterDbImpl.class);
        bind(Connection.class).toInstance(this.createConnextion("jdbc:h2:./dbh2", "sa", ""));
        bind(SQLDialect.class).toInstance(SQLDialect.valueOf("H2"));
    }

    private Connection createConnextion(String url, String userName, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            connection.setAutoCommit(true);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
