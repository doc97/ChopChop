package fi.chop.model.object.gui;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import fi.chop.event.EventData;
import fi.chop.event.Events;
import fi.chop.model.auxillary.Align;
import fi.chop.model.object.util.ValueMeterObject;
import fi.chop.model.world.Player;

public class ReputationMeterObject extends ValueMeterObject {

    private int repLevel = 1;

    public ReputationMeterObject(AssetManager assets, OrthographicCamera camera, Player player) {
        super(assets, camera, player, FillDirection.LEFT, Align.TOP_RIGHT, Align.BOTTOM_RIGHT,
                1, 1, 0, 0, 1, 0,
                "meter-background", "meter-fill", "ZCOOL-30.ttf");
    }

    @Override
    public void dispose() { }

    @Override
    protected String getLabel() {
        String label = "Reputation (R" + repLevel + ")";
        String percentStr = String.format("%.1f", getMeterFillPercentage() * 100);
        return label + ": " + percentStr + "%";
    }

    @Override
    public void handle(Events event, EventData data) {
        if (event == Events.EVT_REPUTATION_CHANGED)
            setMeterValue((float) data.get());
        else if (event == Events.EVT_REPUTATION_LVL_CHANGED)
            repLevel = (int) data.get();
    }
}
