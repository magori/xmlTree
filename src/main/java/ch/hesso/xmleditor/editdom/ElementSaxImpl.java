package ch.hesso.xmleditor.editdom;

import java.util.ArrayList;
import java.util.List;

public class ElementSaxImpl implements Element{

    private List<Element> children;
    private String name;
    private String text = "";

    public ElementSaxImpl(){

    }

    public ElementSaxImpl(String name) {
        this.name = name;
        children = new ArrayList<>();
    }

    public ElementSaxImpl(String name, String text) {
        this.name = name;
        this.text = text;
        children = new ArrayList<>();
    }

    public void addChild(ElementSaxImpl element){
        children.add(element);
    }

    public void setText(String text){
        this.text = text;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public List<Element> getChildren() {
        return children;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getName() {
        return name;
    }
}
