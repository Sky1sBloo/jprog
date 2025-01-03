package org.sky1sbloo.jprog.memory;

public class MemoryCellTypes {
    /**
     * Holder for variant
     */
    public sealed interface Cell permits Int, Number, Bool, StringType {
    }

    public record Int(int value) implements Cell {
    }

    public record Number(Double value) implements Cell {
    }

    public record Bool(boolean value) implements Cell {
    }

    public record StringType(String value) implements Cell {
    }
}
