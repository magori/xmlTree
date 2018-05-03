package ch.hesso.xmleditor.persistence;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.generated.tables.File;
import org.jooq.generated.tables.records.FileRecord;
import org.jooq.impl.DSL;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.SQLException;

import static org.jooq.generated.tables.File.FILE;

public class PersisterDbImpl implements Persister, AutoCloseable {

    private Connection connection;
    private DSLContext dslContext;

    @Inject
    public PersisterDbImpl(Connection connection, SQLDialect sqlDialect) {
        this.connection = connection;
        dslContext = DSL.using(connection, SQLDialect.valueOf("H2"));
    }


    @Override
    public void save(String id, String content) {
        FileRecord fileRecord = dslContext.fetchOne(FILE, FILE.DOC_NAME.eq(id));
        if (fileRecord == null) {
            dslContext.insertInto(File.FILE)
                      .set(File.FILE.CONTENT, content)
                      .set(File.FILE.DOC_NAME, id)
                      .execute();
        } else {
            fileRecord.setContent(content);
            fileRecord.setDocName(id);
            fileRecord.update();
        }
        commit();
    }


    @Override
    public String load(String id) {
        FileRecord fileRecord = dslContext.fetchOne(FILE, FILE.DOC_NAME.eq(id));
        if (fileRecord == null) {
            return null;
        }
        return fileRecord.getContent();
    }


    @Override
    public void close() throws SQLException {
        this.connection.close();
    }

    int delete(String id) {
        return dslContext.delete(FILE).where(FILE.DOC_NAME.eq(id)).execute();
    }

    void commit() {
        try {
            this.connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
