package ch.hesso.xmleditor.editdom;

import java.util.List;

public interface Element {
    List<Element> getChildren();

    String getText();

    String getName();

}
