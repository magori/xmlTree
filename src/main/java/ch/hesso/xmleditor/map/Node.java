package ch.hesso.xmleditor.map;

import java.util.List;

public interface Node {
    String getId();

    String getName();

    String getText();

    List<NodeImpl> getChildren();
}
