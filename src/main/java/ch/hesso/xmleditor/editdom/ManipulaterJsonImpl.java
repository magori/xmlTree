package ch.hesso.xmleditor.editdom;

import ch.hesso.xmleditor.persistence.Persister;
import com.google.gson.*;
import org.jooq.tools.json.JSONObject;
import org.jooq.util.derby.sys.Sys;

import java.util.List;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class ManipulaterJsonImpl implements Manipulater {
    private Persister persister;
    private JsonElement element;
    private String idDocument;

    //Obligatoir pour l'introspection, afin de determiner le type
    public ManipulaterJsonImpl() {
    }

    @Inject
    public ManipulaterJsonImpl(Persister persister) {
        this.persister = persister;
    }

    @Override
    public String forType() {
        return ManipulaterType.JSON.getName();
    }

    @Override
    public Element getRootElement() {
        return new ElementJsonImpl(element);
    }

    @Override
    public Element editElement(String id, String newText) {
        String[] ids = id.split("-");
        JsonObject jsonObject = element.getAsJsonObject();
        for (int i = 0; i < (ids.length - 1); i++) {
            String property = resolveProperty(ids[i], jsonObject);
            jsonObject = jsonObject.get(property).getAsJsonObject();
        }

        String property = resolveProperty(ids[ids.length - 1], jsonObject);
        System.out.println("[ManipulaterJsonImpl] " + property + "/" + jsonObject.get(property).getAsString());
        jsonObject.addProperty(property, newText);
        return new ElementJsonImpl(jsonObject.get(property));
    }

    /*@Override
    public Element editElementName(String id, String newName) {
        String[] ids = id.split("-");
        JsonObject jsonObject = element.getAsJsonObject();
        for (int i = 0; i < (ids.length - 1); i++) {
            String property = resolveProperty(ids[i], jsonObject);
            jsonObject = jsonObject.get(property).getAsJsonObject();
        }

        String oldProperty = resolveProperty(ids[ids.length - 1], jsonObject);
        JsonElement el = jsonObject.get(oldProperty);
        jsonObject.remove(oldProperty);
        jsonObject.add(newName, el);
        return new ElementJsonImpl(jsonObject.get(newName));
    }*/

    @Override
    public String addElementToParent(String parentId, String name, String text) {
        String[] ids = parentId.split("-");

        // Look for right element and his parent
        ElementJsonImpl elJSON = new ElementJsonImpl(element);
        ElementJsonImpl elJSONParent = new ElementJsonImpl(element);
        for (int i = 0; i < ids.length; i++) {
            elJSON = (ElementJsonImpl) elJSON.getChildren().get(Integer.valueOf(ids[i]));
            if(i < ids.length - 1){
                elJSONParent = elJSON;
            }
        }

        if(elJSON.element.isJsonObject()){
            elJSON.addProperty(name, text);
            return parentId + "-" + (elJSON.getChildren().size() - 1);
        }else{
            // new JSON Element
            String nameProperty = elJSON.getName();
            JsonObject newObject = new JsonObject();
            newObject.addProperty(name, text);

            // Remove all children
            JsonObject elementParent = elJSONParent.element.getAsJsonObject();
            JsonObject elementParentCopy = elementParent.deepCopy();
            for(String property: elementParentCopy.keySet()){
                elementParent.remove(property);
            }

            // Add all children again to respect the order
            for(String property: elementParentCopy.keySet()) {
                if (property.equals(nameProperty)) {
                    elementParent.add(nameProperty, newObject);
                } else {
                    if (elementParentCopy.get(property).isJsonPrimitive())
                        elementParent.addProperty(property, elementParentCopy.get(property).getAsString());
                    else
                        elementParent.add(property, elementParentCopy.get(property).getAsJsonObject());
                }
            }
            return parentId + "-" + 0; // always 0 since it is the first child of a previous property
        }

    }

    private String resolveProperty(String id, JsonObject jsonObject) {
        Map<Integer, String> indexToKey = new HashMap<>();
        jsonObject.keySet().forEach(k -> indexToKey.put(indexToKey.size(), k));
        return indexToKey.get(Integer.valueOf(id));
    }

    public void saveDocument() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(element);
        this.persister.save(this.idDocument, json);
    }

    @Override
    public void load(String idDocument) {
        this.idDocument = idDocument;
        this.parse(this.persister.load(idDocument));
    }

    @Override
    public List<String> fetchDbList() {
        return persister.fetchDbList();
    }

    void parse(String json) {
        JsonParser parser = new JsonParser();
        this.element = parser.parse(json);
    }
}
