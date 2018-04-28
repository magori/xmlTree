package ch.hesso.xmleditor.editdom;

import ch.hesso.xmleditor.persistence.Persister;
import com.google.gson.*;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class ManipulaterJsonImpl implements DomManipulater {
    private final Persister persister;
    private JsonElement element;
    private String idDocument;

    @Inject
    public ManipulaterJsonImpl(Persister persister) {
        this.persister = persister;
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
            jsonObject = jsonObject.getAsJsonObject();
        }
        Map<Integer, String> indexToKey = new HashMap<>();
        jsonObject.keySet().forEach(k -> indexToKey.put(indexToKey.size(), k));
        String property = indexToKey.get(Integer.valueOf(ids[ids.length - 1]));
        jsonObject.addProperty(property, newText);
        return new ElementJsonImpl(jsonObject.get(property));
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

    void parse(String json) {
        JsonParser parser = new JsonParser();
        this.element = parser.parse(json);
    }
}
