package ch.hesso.xmleditor.persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PersisterDbImplTest {

    @Test
    void save() {
        PersisterDbImpl persister = new PersisterDbImpl();
        persister.save("testnew", "{}");
        persister.delete("testnew");
        persister.commit();
        persister.save("testnew", "{}");
        persister.save("testnew", "{}");
    }

    @Test
    void load() {
        String json = "{\n" +
                "  \"brand\": \"Toyota\",\n" +
                "  \"doors\": 5,\n" +
                "  \"options\": {\n" +
                "    \"automatic\": \"TRUE\",\n" +
                "    \"gps\": \"google\"\n" +
                "  }\n" +
                "}";
        PersisterDbImpl persister = new PersisterDbImpl();
        persister.save("monF", json);
        Assertions.assertThat(persister.load("monF")).isEqualTo(json);
        Assertions.assertThat(persister.load("dkdkddk")).isNull();
    }
}