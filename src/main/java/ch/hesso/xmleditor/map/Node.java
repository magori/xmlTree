package ch.hesso.xmleditor.map;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final String id;
    private final String name;
    private final String text;
    private final List<Node> children;

    public Node(String id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.children = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    public String getText() {
        return text;
    }

    public List<Node> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", children=" + children +
                '}';
    }
}
