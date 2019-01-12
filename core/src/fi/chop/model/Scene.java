package fi.chop.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fi.chop.Chop;
import fi.chop.event.EventListener;
import fi.chop.model.object.GameObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Scene {

    private List<GameObject> objects;
    private List<GameObject> toAdd;

    public Scene() {
        objects = new ArrayList<>();
        toAdd = new ArrayList<>();
    }

    public void update(float delta) {
        objects.addAll(toAdd);
        toAdd.clear();

        for (Iterator<GameObject> it = objects.iterator(); it.hasNext();) {
            GameObject obj = it.next();
            obj.update(delta);
            if (obj.isDead()) {
                if (obj instanceof EventListener)
                    Chop.events.removeListener((EventListener) obj);
                it.remove();
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (GameObject obj : objects)
            obj.render(batch);
    }

    public <T extends GameObject> T findOne(Class<T> clazz) {
        for (GameObject obj : objects) {
            if (clazz.isInstance(obj))
                return clazz.cast(obj);
        }
        return null;
    }

    public <T extends GameObject> List<T> findAll(Class<T> clazz) {
        List<T> ret = new ArrayList<>();
        for (GameObject obj : objects) {
            if (clazz.isInstance(obj))
                ret.add(clazz.cast(obj));
        }
        return ret;
    }

    public void clear() {
        objects.clear();
        toAdd.clear();
    }

    public void add(GameObject... objects) {
        toAdd.addAll(Arrays.asList(objects));
    }

    public int getObjectCount() {
        return objects.size();
    }

    public boolean isEmpty() {
        return objects.isEmpty();
    }
}
