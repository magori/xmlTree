package ch.hesso.xmleditor.editdom;

import ch.hesso.xmleditor.persistence.Persister;

import javax.inject.Inject;
import java.util.List;

public class ManipulaterBridge {
    private Persister persister;

    public ManipulaterBridge(Persister persister) {
        this.persister = persister;
    }

    public List<String> fetchDbList() {
        return persister.fetchDbList();
    }
}
