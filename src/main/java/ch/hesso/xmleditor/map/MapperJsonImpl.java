package ch.hesso.xmleditor.map;

import ch.hesso.xmleditor.editdom.DomManipulater;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.inject.Inject;

public class MapperJsonImpl implements Mapper {
    private final DomManipulater domManipulater;

    @Inject
    public MapperJsonImpl(DomManipulater domManipulater) {
        this.domManipulater = domManipulater;
    }

    @Override
    public void editNode(String id, String newText) {
        this.domManipulater.editElement(id, newText);
    }

    @Override
    public void saveTree() {
        this.domManipulater.saveDocument();
    }

    @Override
    public NodeImpl createTree(String idDocument) {
        this.domManipulater.load(idDocument);
        NodeImpl node = new NodeImpl(null, domManipulater.getRootElement().getName(), null);
        return null;
    }

    void parse(String json) {
        // Gson gson = new Gson();
        //JsonElement element = gson.fromJson(json, JsonElement.class);

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);// Returns Root element(
        final NodeImpl root = new NodeImpl(null, "root", null);
        createTree(element, "0", null, root);
        System.out.println(root);
    }

    private NodeImpl createTree(JsonElement element, String id, String name, NodeImpl parent) {
        if (!element.isJsonObject()) {
            return new NodeImpl(id, name, element.getAsString());
        } else {
            String[] names = element.getAsJsonObject().keySet().toArray(new String[element.getAsJsonObject().size()]);
            for (int i = 0; i < names.length; i++) {
                String newId = id + "-" + i;
                if (id == null) {
                    newId = i + "";
                }
                NodeImpl nodeParent = new NodeImpl(newId, names[i], null);
                NodeImpl nodeChild = createTree(element.getAsJsonObject().get(names[i]), newId, names[i], nodeParent);
                parent.getChildren().add(nodeChild);
            }
        }
        return parent;
    }


}
