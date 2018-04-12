package ch.hesso.xmleditor.persistence;

public interface Persister {
    void save(String id, String content);

    String load(String id);
}
