package ch.hesso.xmleditor.map;

public interface Mapper {

    void editNode(String id, String newText);

    void saveTree();

    Node createTree(String documentId);
}
