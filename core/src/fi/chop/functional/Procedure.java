package fi.chop.functional;

@FunctionalInterface
public interface Procedure {
    void run();

    default Procedure and(Procedure after) {
        return () -> {
            this.run();
            after.run();
        };
    }

    default Procedure compose(Procedure before) {
        return () -> {
            before.run();
            this.run();
        };
    }
}
