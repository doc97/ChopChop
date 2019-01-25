package fi.chop.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.model.object.GameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Scene {

    private List<String> names;
    private Map<String, Layer> layers;

    public Scene() {
        names = new ArrayList<>();
        layers = new HashMap<>();
    }

    public void addObjects(String layerName, GameObject... objects) {
        if (!names.contains(layerName))
            throw new IllegalArgumentException("No layer with name: " + layerName);
        for (GameObject obj : objects) {
            layers.get(layerName).add(obj);
            layers.get(layerName).add(obj.getChildren());
        }
    }

    public void addQueued() {
        for (String name : names)
            layers.get(name).addQueued();
    }

    public GameObject findOne(Predicate<GameObject> predicate) {
        for (String name : names) {
            GameObject result = layerFindOne(name, predicate);
            if (result != null)
                return result;
        }
        return null;
    }

    public GameObject layerFindOne(String layerName, Predicate<GameObject> predicate) {
        if (!names.contains(layerName))
            throw new IllegalArgumentException("No layer with name: " + layerName);
        return layers.get(layerName).findOne(predicate);
    }

    public <T extends GameObject> T findOne(Class<T> clazz) {
        for (String name : names) {
            T result = layerFindOne(name, clazz);
            if (result != null)
                return result;
        }
        return null;
    }

    public <T extends GameObject> T layerFindOne(String layerName, Class<T> clazz) {
        if (!names.contains(layerName))
            throw new IllegalArgumentException("No layer with name: " + layerName);
        return layers.get(layerName).findOne(clazz);
    }

    public List<GameObject> findAll(Predicate<GameObject> predicate) {
        List<GameObject> result = new ArrayList<>();
        for (String name : names) {
            List<GameObject> subResult = layerFindAll(name, predicate);
            result.addAll(subResult);
        }
        return result;
    }

    public List<GameObject> layerFindAll(String layerName, Predicate<GameObject> predicate) {
        if (!names.contains(layerName))
            throw new IllegalArgumentException("No layer with name: " + layerName);
        return layers.get(layerName).findAll(predicate);
    }

    public <T extends GameObject> List<T> findAll(Class<T> clazz) {
        List<T> result = new ArrayList<>();
        List<GameObject> objects = findAll(clazz::isInstance);
        for (GameObject obj : objects)
            result.add(clazz.cast(obj));
        return result;
    }

    public <T extends GameObject> List<T> layerFindAll(String layerName, Class<T> clazz) {
        if (!names.contains(layerName))
            throw new IllegalArgumentException("No layer with name: " + layerName);
        return layers.get(layerName).findAll(clazz);
    }

    public void killAll(Predicate<GameObject> predicate) {
        for (String name : names)
            layers.get(name).killAll(predicate);
    }

    public void layerKillAll(String layerName, Predicate<GameObject> predicate) {
        if (!names.contains(layerName))
            throw new IllegalArgumentException("No layer with name: " + layerName);
        layers.get(layerName).killAll(predicate);
    }

    public void clear() {
        for (String name : names)
            layers.get(name).clear();
    }

    public void layerClear(String layerName) {
        if (!names.contains(layerName))
            throw new IllegalArgumentException("No layer with name: " + layerName);
        layers.get(layerName).clear();
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
