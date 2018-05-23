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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * Le but de cette class est de gérer l'injection de dépendance et aussi de charger les plugins via le serviceLoader de java.
 */
class XmlEditorManagerModuleGuice extends AbstractModule {

    @Override
    protected void configure() {
        ServiceLoader service = ServiceLoader.load(ManipulaterFactory.class, Mapper.class, Persister.class, Manipulater.class);
        addBindingForExtentionFile(service);

        bind(ManipulaterFactory.class).to(service.getFirstOrDefault(ManipulaterFactory.class, ManipulaterFactoryImpl.class));
        bind(Mapper.class).to(service.getFirstOrDefault(Mapper.class, MapperImpl.class));

        Properties properties = loadConfig();
        PropertiePersisterType persisterType = resolvePropertiePersister(properties);
        if (persisterType.isDb()) {
            bindConfigForPersister(properties);
            bind(View.class).to(service.getFirstOrDefault(View.class, ViewDbImpl.class));
            bind(Persister.class).to(service.getFirstOrDefault(Persister.class, PersisterDbImpl.class));
        } else if (persisterType.isFile()) {
            bind(View.class).to(service.getFirstOrDefault(View.class, ViewFileImpl.class));
            bind(Persister.class).to(service.getFirstOrDefault(Persister.class, PersisterFileImpl.class));
        }
    }

    private void addBindingForExtentionFile(ServiceLoader service) {
        MapBinder<String, Manipulater> mapBinder = MapBinder.newMapBinder(binder(), String.class, Manipulater.class);

        List<? super Manipulater> list = new ArrayList<>();
        list.add(new ManipulaterJdomImpl());
        service.apply(Manipulater.class, list,
                      (Manipulater m) -> mapBinder.addBinding(m.forType()).to(m.getClass()), m1 -> m1.forType().toLowerCase());
    }

    private void bindConfigForPersister(Properties properties) {
        String url = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.user");
        String password = properties.getProperty("jdbc.password");
        String dialect = properties.getProperty("sql.dialect");
        bind(Connection.class).toInstance(this.createConnection(url, user, password));
        bind(SQLDialect.class).toInstance(SQLDialect.valueOf(dialect));
    }

    private PropertiePersisterType resolvePropertiePersister(Properties properties) {
        String persister = properties.getProperty(PropertiePersisterType.getKey());
        PropertiePersisterType persisterType = PropertiePersisterType.FILE;
        if (!Objects.isNull(persister)) {
            persisterType = PropertiePersisterType.valueOf(persister.toUpperCase());
        }
        return persisterType;
    }

    private Properties loadConfig() {
        try (FileInputStream in = new FileInputStream("./config.properties")) {
            Properties applicationProps = new Properties();
            applicationProps.load(in);
            return applicationProps;
        } catch (IOException e) {
            throw new RuntimeException("Le fichier de configuration('config.properties') n'a pas été trouvé, il faut le rajouter dans le même " +
                                               "dossier que le jar !", e);
        }
    }

    private Connection createConnection(String url, String userName, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, userName, password);
            connection.setAutoCommit(true);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
