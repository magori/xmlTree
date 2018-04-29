package ch.hesso.xmleditor.persistence;

import ch.hesso.xmleditor.editdom.ManipulaterJsonImpl;
import org.junit.jupiter.api.Test;

class PersisterDbImplTest {

    @Test
    void save() {
        PersisterDbImpl persister = new PersisterDbImpl();
       // persister.save("monF", "{}");
       // System.out.println(persister.load("test.file2"));
    }

    @Test
    void load() {
        PersisterDbImpl persister = new PersisterDbImpl();
        persister.save("monF", "{\n" +
                "  \"brand\": \"Toyota\",\n" +
                "  \"doors\": 5,\n" +
                "  \"options\": {\n" +
                "    \"automatic\": \"TRUE\",\n" +
                "    \"gps\": \"google\"\n" +
                "  }\n" +
                "}");
        System.out.println(persister.load("monF"));
        ManipulaterJsonImpl manipulaterJson = new ManipulaterJsonImpl(persister);
        manipulaterJson.load("monF");
        System.out.println(manipulaterJson.getRootElement().getChildren().get(1).getText());
    }
}