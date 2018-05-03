package ch.hesso.xmleditor.display;

import ch.hesso.xmleditor.editdom.Manipulater;
import ch.hesso.xmleditor.editdom.ManipulaterJsonImpl;
import ch.hesso.xmleditor.map.Mapper;
import ch.hesso.xmleditor.map.MapperImpl;
import ch.hesso.xmleditor.persistence.Persister;
import ch.hesso.xmleditor.persistence.PersisterDbImpl;
import com.google.inject.AbstractModule;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

class XmlEditorManagerModuleGuice extends AbstractModule {
    @Override
    protected void configure() {
        bind(Mapper.class).to(MapperImpl.class);
        bind(Manipulater.class).to(ManipulaterJsonImpl.class);

        // create and load default properties
        Properties defaultProps = new Properties();
        try {
            FileInputStream in = new FileInputStream("./config/config.properties");
            defaultProps.load(in);
            // create application properties with default
            Properties applicationProps = new Properties(defaultProps);
            applicationProps.load(in);
            System.out.println(applicationProps.getProperty("persister"));
            in.close();
        } catch (Exception e) {
            System.err.println(e);
        }


        bind(Persister.class).to(PersisterDbImpl.class);
    }
}
