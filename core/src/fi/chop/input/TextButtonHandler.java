package fi.chop.input;

import com.badlogic.gdx.graphics.Color;
import fi.chop.functional.Procedure;
import fi.chop.model.object.GameObject;
import fi.chop.model.object.util.TextObject;
import fi.chop.model.object.util.TextObjectStyle;

public class TextButtonHandler extends TouchHandler {

    private Procedure onClick;
    private TextObjectStyle normalStyle;
    private TextObjectStyle hoverStyle;

    public TextButtonHandler(Procedure onClick) {
        this.onClick = onClick;
        this.normalStyle = new TextObjectStyle();
        this.hoverStyle = new TextObjectStyle().bgColor(Color.FIREBRICK).tint(Color.YELLOW);
    }

    public TextButtonHandler(Procedure onClick, TextObjectStyle normalStyle, TextObjectStyle hoverStyle) {
        this.onClick = onClick;
        this.normalStyle = normalStyle;
        this.hoverStyle = hoverStyle;
    }

    @Override
    public void touchDown(GameObject obj, float worldX, float worldY, int pointer, int button) {
        onClick.run();
    }

    @Override
    public void enter(GameObject obj, float worldX, float worldY) {
        TextObject txt = (TextObject) obj;
        txt.style(hoverStyle);
    }

    @Override
    public void exit(GameObject obj, float worldX, float worldY) {
        TextObject txt = (TextObject) obj;
        txt.style(normalStyle);
    }
}
