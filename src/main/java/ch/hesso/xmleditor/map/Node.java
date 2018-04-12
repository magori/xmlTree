package ch.hesso.xmleditor.map;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String id;
    private String name;
    private String text;
    private List<Node> children;

    public Node(String id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.children = new ArrayList<>();
    }

    public Node(String id, String name, String text, List<Node> children) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

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
