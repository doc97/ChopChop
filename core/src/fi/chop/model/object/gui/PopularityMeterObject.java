package fi.chop.model.object.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import fi.chop.event.EventData;
import fi.chop.event.Events;
import fi.chop.model.auxillary.Align;
import fi.chop.model.object.util.ValueMeterObject;
import fi.chop.model.world.Player;
import fi.chop.model.world.WorldState;

public class PopularityMeterObject extends ValueMeterObject {

    public PopularityMeterObject(AssetManager assets, OrthographicCamera camera, WorldState world, Player player) {
        super(assets, camera, world, player, FillDirection.LEFT, Align.TOP_RIGHT, Align.BOTTOM_RIGHT,
                1, 1, 0, 0, 1, 0,
                "meter-background", "meter-fill", "ZCOOL-30.ttf");
    }

    @Override
    protected String getLabel() {
        String type = "Popularity";
        String percentStr = String.format("%.1f", getMeterFillPercentage() * 100);
        return type + ": " + percentStr + "%";
    }

    @Override
    public void handle(Events event, EventData data) {
        if (event == Events.EVT_POPULARITY_CHANGED)
            setMeterValue((float) data.get());
    }
}
