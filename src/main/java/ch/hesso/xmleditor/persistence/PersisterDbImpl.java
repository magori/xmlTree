package ch.hesso.xmleditor.persistence;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.generated.tables.File;
import org.jooq.generated.tables.records.FileRecord;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.jooq.generated.tables.File.FILE;

public class PersisterDbImpl implements Persister, AutoCloseable {
    private Connection connection;
    private DSLContext create;

    public PersisterDbImpl() {
        String userName = "sa";
        String password = "";
        String url = "jdbc:h2:./dbh2";

        try {
            connection = DriverManager.getConnection(url, userName, password);
            connection.setAutoCommit(true);
            create = DSL.using(connection, SQLDialect.MYSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(String id, String content) {
        FileRecord fileRecord = create.fetchOne(FILE, FILE.DOC_NAME.eq(id));
        if (fileRecord == null) {
            create.insertInto(File.FILE)
                  .set(File.FILE.CONTENT, content)
                  .set(File.FILE.DOC_NAME, id)
                  .execute();
        } else {
            fileRecord.setContent(content);
            fileRecord.setDocName(id);
            fileRecord.update();
        }
        try {
            this.connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String load(String id) {
        FileRecord fileRecord = create.fetchOne(FILE, FILE.DOC_NAME.eq(id));
        if (fileRecord == null) {
            return null;
        }
        return fileRecord.getContent();
    }

    @Override
    public void close() throws SQLException {
        this.connection.close();
    }
}
