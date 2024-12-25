package dev.tildejustin.no_peaceful;

public interface LevelPropertiesInterface {
    default boolean no_peaceful$isPeacefulAllowed() {
        throw new RuntimeException();
    }
}
