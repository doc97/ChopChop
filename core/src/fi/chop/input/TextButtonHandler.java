package fi.chop.input;

import com.badlogic.gdx.graphics.Color;
import fi.chop.functional.Procedure;
import fi.chop.model.object.GameObject;
import fi.chop.model.object.TextObject;

public class TextButtonHandler extends TouchHandler {

    private Procedure onClick;

    public TextButtonHandler(Procedure onClick) {
        this.onClick = onClick;
    }

    @Override
    public void touchDown(GameObject obj, float worldX, float worldY, int pointer, int button) {
        onClick.run();
    }

    @Override
    public void enter(GameObject obj, float worldX, float worldY) {
        TextObject txt = (TextObject) obj;
        txt.tint(Color.YELLOW);
        txt.bgColor(Color.FIREBRICK);
    }

    @Override
    public void exit(GameObject obj, float worldX, float worldY) {
        TextObject txt = (TextObject) obj;
        txt.tint(Color.WHITE);
        txt.bgColor(null);
    }
}
