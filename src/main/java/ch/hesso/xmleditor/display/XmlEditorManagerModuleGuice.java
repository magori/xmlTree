package ch.hesso.xmleditor.display;

import ch.hesso.xmleditor.editdom.*;
import ch.hesso.xmleditor.map.Mapper;
import ch.hesso.xmleditor.map.MapperImpl;
import ch.hesso.xmleditor.persistence.Persister;
import ch.hesso.xmleditor.persistence.PersisterDbImpl;
import ch.hesso.xmleditor.persistence.PersisterFileImpl;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import org.jooq.SQLDialect;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class XmlEditorManagerModuleGuice extends AbstractModule {


    @Override
    protected void configure() {
        Properties properties = loadConfig();

        MapBinder<ManipulaterType, Manipulater> mapBinder = MapBinder.newMapBinder(binder(), ManipulaterType.class, Manipulater.class);
        mapBinder.addBinding(ManipulaterType.JSON).to(ManipulaterJsonImpl.class);
        mapBinder.addBinding(ManipulaterType.XML).to(ManipulaterJdomImpl.class);

        bind(ManipulaterFactory.class).to(ManipulaterFactoryImpl.class);
        bind(Mapper.class).to(MapperImpl.class);
        String persister = properties.getProperty("persister");
        if ("db".equalsIgnoreCase(persister)) {
            String url = properties.getProperty("jdbc.url");
            String user = properties.getProperty("jdbc.user");
            String password = properties.getProperty("jdbc.password");
            String dialect = properties.getProperty("sql.dialect");
            bind(Connection.class).toInstance(this.createConnextion(url, user, password));
            bind(SQLDialect.class).toInstance(SQLDialect.valueOf(dialect));
            bind(Persister.class).to(PersisterDbImpl.class);
        } else if ("file".equalsIgnoreCase(persister)) {
            bind(Persister.class).to(PersisterFileImpl.class);
        } else {
            bind(Persister.class).to(PersisterFileImpl.class);
        }
    }

    private Properties loadConfig() {


        Properties defaultProps = new Properties();
        try {
            FileInputStream in = new FileInputStream("./config.properties");
            defaultProps.load(in);
            // create application properties with default
            Properties applicationProps = new Properties(defaultProps);
            applicationProps.load(in);
            System.out.println(applicationProps.getProperty("persister"));
            in.close();
        } catch (Exception e) {
            System.err.println(e);
        }

        return defaultProps;
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
