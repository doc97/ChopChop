package fi.chop.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene {

    private List<String> names;
    private Map<String, Layer> layers;

    public Scene() {
        names = new ArrayList<>();
        layers = new HashMap<>();
    }

    public void update(float delta) {
        /* Layers are updated from "bottom" to "top" */
        for (String name : names)
            layers.get(name).update(delta);
    }

    public void render(SpriteBatch batch) {
        /* Layers are rendered from "bottom" to "top" */
        for (String name : names)
            layers.get(name).render(batch);
    }

    public void addLayer(String name, Layer layer) {
        layers.put(name, layer);
        if (!names.contains(name))
            names.add(name);
    }

    public void moveTop(String name) {
        int index = validateName(name);
        if (index == names.size() - 1)
            return;
        names.add(names.get(index));
        names.remove(index);
    }

    public void moveUp(String name) {
        int index = validateName(name);
        if (index == names.size() - 1)
            return;
        names.add(index + 2, names.get(index));
        names.remove(index);
    }

    public void moveDown(String name) {
        int index = validateName(name);
        if (index == 0)
            return;

        // If moving second element, it will be added to index 0
        names.add(Math.max(index - 2, 0), names.get(index));
        names.remove(index + 1);
    }

    public void moveBottom(String name) {
        int index = validateName(name);
        if (index == 0)
            return;
        names.add(0, names.get(index));
        names.remove(index + 1);
    }

    public int getLayerCount() {
        return names.size();
    }

    public int getLayerOrder(String name) {
        return names.indexOf(name);
    }

    public String[] getLayerNames() {
        return names.toArray(new String[0]);
    }

    private int validateName(String name) {
        int index = names.indexOf(name);
        if (index == -1)
            throw new IllegalArgumentException("No layer with name: " + name);
        return index;
    }
}
