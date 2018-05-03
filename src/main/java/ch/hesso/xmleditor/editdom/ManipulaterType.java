package ch.hesso.xmleditor.editdom;

import java.util.Objects;

public enum ManipulaterType {

    JSON("json"),
    XML("xml");

    private String name;

    ManipulaterType(String name) {
        this.name = name;
    }

    public static ManipulaterType parse(String type) {
        Objects.requireNonNull(type, "the type is null");
        if (JSON.name.equalsIgnoreCase(type)) {
            return JSON;
        } else if (XML.name.equalsIgnoreCase(type)) {
            return XML;
        } else {
            throw new RuntimeException("This type " + type + " is not managed!");
        }
    }

    public String getName() {
        return name;
    }
}
