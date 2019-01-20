package fi.chop.engine;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.model.object.GameObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class Layer {

    private List<GameObject> objects;
    private List<GameObject> toAdd;
    private int nextId;

    public Layer() {
        objects = new ArrayList<>();
        toAdd = new ArrayList<>();
    }

    public void update(float delta) {
        addQueued();

        for (Iterator<GameObject> it = objects.iterator(); it.hasNext();) {
            GameObject obj = it.next();
            obj.update(delta);
            if (obj.isDead()) {
                Chop.events.removeListener(obj);
                it.remove();
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (GameObject obj : objects)
            obj.render(batch);
    }

    public GameObject findOne(Predicate<GameObject> predicate) {
        for (GameObject object : objects) {
            if (predicate.test(object))
                return object;
        }
        return null;
    }

    public <T extends GameObject> T findOne(Class<T> clazz) {
        GameObject obj = findOne(clazz::isInstance);
        if (obj == null)
            return null;
        return clazz.cast(obj);
    }

    public List<GameObject> findAll(Predicate<GameObject> predicate) {
        List<GameObject> ret = new ArrayList<>();
        for (GameObject object : objects) {
            if (predicate.test(object))
                ret.add(object);
        }
        return ret;
    }

    public <T extends GameObject> List<T> findAll(Class<T> clazz) {
        List<GameObject> result = findAll(clazz::isInstance);
        List<T> ret = new ArrayList<>();
        for (GameObject obj : result)
            ret.add(clazz.cast(obj));
        return ret;
    }

    public int killAll(Predicate<GameObject> predicate) {
        int count = 0;
        for (GameObject object : objects) {
            if (predicate.test(object)) {
                count++;
                object.die();
            }
        }
        return count;
    }

    public void clear() {
        objects.clear();
        toAdd.clear();
    }

    public void add(GameObject... objects) {
        toAdd.addAll(Arrays.asList(objects));
    }

    public void addQueued() {
        for (GameObject obj : toAdd) {
            obj.setID(nextId++);
            objects.add(obj);
        }
        toAdd.clear();
    }

    public int getObjectCount() {
        return objects.size();
    }

    public boolean isEmpty() {
        return objects.isEmpty();
    }
}
