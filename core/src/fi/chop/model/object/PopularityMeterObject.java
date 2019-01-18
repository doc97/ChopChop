package fi.chop.model.object;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class PopularityMeterObject extends ValueMeterObject {

    public PopularityMeterObject(AssetManager assets, OrthographicCamera camera) {
        super(assets, camera, FillDirection.LEFT, TextOriginX.RIGHT, TextOriginY.BOTTOM,
                0, 0, 0, 5,
                "meter-background", "meter-fill", "ZCOOL-30.ttf");
    }

    @Override
    public void update(float delta) { }

    @Override
    protected String getLabel() {
        String type = "Popularity";
        String percentStr = String.format("%.1f", getMeterFillPercentage() * 100);
        return type + ": " + percentStr + "%";
    }
}
