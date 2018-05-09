package ch.hesso.xmleditor.editdom;

import ch.hesso.xmleditor.persistence.Persister;
import com.google.gson.*;
import java.util.List;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class ManipulaterJsonImpl implements Manipulater {
    private Persister persister;
    private JsonElement element;
    private String idDocument;

    //Obligatoir pour l'introspection, afin de determiner le type
    public ManipulaterJsonImpl() { }

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
        jsonObject.addProperty(property, newText);
        return new ElementJsonImpl(jsonObject.get(property));
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
