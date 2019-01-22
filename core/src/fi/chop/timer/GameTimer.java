package fi.chop.timer;

import java.util.PriorityQueue;

public class GameTimer {

    private static final float EPSILON = 0.000001f;

    private PriorityQueue<DelayedAction> actions;
    private float time;

    public GameTimer() {
        actions = new PriorityQueue<>((a, b) -> Float.compare(a.time, b.time));
    }

    public void update(float delta) {
        time += delta;
        while (!actions.isEmpty() && actions.peek().time < time + EPSILON)
            actions.poll().action.call();
    }

    public DelayedAction addAction(float delaySec, TimedAction action) {
        if (delaySec < 0)
            throw new IllegalArgumentException("delaySec must not be negative");
        if (delaySec == 0) {
            action.call();
            return null;
        }

        DelayedAction delayedAction = new DelayedAction();
        delayedAction.time = time + delaySec;
        delayedAction.action = action;
        actions.add(delayedAction);
        return delayedAction;
    }


    public void clear() {
        actions.clear();
    }

    public float getGlobalTime() {
        return time;
    }

    public float getTimeLeft(DelayedAction action) {
        return Math.max(action.time - time, 0);
    }

    public class DelayedAction {
        public float time;
        private TimedAction action;
    }
}
