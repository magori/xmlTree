package ch.hesso.xmleditor.persistence;

import org.assertj.core.api.Assertions;
import org.jooq.SQLDialect;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class PersisterDbImplTest {

    private static final PersisterDbImpl persister = new PersisterDbImpl(createConnextion(), SQLDialect.H2);

    private static Connection createConnextion() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:./dbh2", "sa", "");
            connection.setAutoCommit(true);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void save() {
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
        persister.save("monF", json);
        Assertions.assertThat(persister.load("monF")).isEqualTo(json);
        Assertions.assertThat(persister.load("dkdkddk")).isNull();
    }
}