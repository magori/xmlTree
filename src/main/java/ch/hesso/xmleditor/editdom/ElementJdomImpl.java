package ch.hesso.xmleditor.editdom;

import java.util.List;
import java.util.stream.Collectors;

class ElementJdomImpl implements Element {

    private org.jdom2.Element jdomElemnt;

    public ElementJdomImpl(org.jdom2.Element jdomElemnt) {
        this.jdomElemnt = jdomElemnt;
        this.jdomElemnt.getChildren();
    }

    public List<Element> getChildren() {
        return this.jdomElemnt.getChildren().stream().map(ElementJdomImpl::new).collect(Collectors.toList());
    }

    @Override
    public String getText() {
        return this.jdomElemnt.getText();
    }

    @Override
    public String getName() {
        return this.jdomElemnt.getName();
    }

}
